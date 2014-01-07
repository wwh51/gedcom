import sys
import re
import xml.etree.ElementTree as ET


def create_node_by_line(line, hasChild=False):
    if line[2] is None:
        return ET.Element(line[1])
    name = line[1]
    value = line[2].strip()
    id_pattern = re.compile(r'(@.*@)')
    node_id = id_pattern.match(name) # the first one may be id 
    if node_id:
        node_id = node_id.group(1)
        name = value
    else:
        node_id = id_pattern.match(value) # the second one is id 
        if node_id:
            node_id = node_id.group(1)
    new_node = ET.Element(name.lower())
    if node_id:
        new_node.set('id', node_id)
    elif hasChild:
        new_node.set('value', value)
    else:
        new_node.text = value
    return new_node


def AddNewNode(node_list, record, next_record):
    new_node_level = record[0]
    new_node = create_node_by_line(record, new_node_level < next_record[0])
    if new_node_level > 0 and node_list[new_node_level - 1] is not None:
        node_list[new_node_level - 1].append(new_node)
    node_list[new_node_level + 1:
              ] = [None for i in range(new_node_level + 1, len(node_list))]
    node_list[new_node_level] = new_node


def gedcom_parser(input_file, output_file):
    try:
        with open(input_file, 'rt') as f:
            file_text = f.read()
    except:
        print('Error: %s does not exist!' % input_file)
        return
    file_text = file_text.strip('\n').split('\n')

    root = ET.Element('gedcom')
    m_re = re.compile(r'\s*(\d+)\s+(\S+)(\s+.*)*')
    prev_record = None
    # max depth of the sample file is 3 (0,1,2)
    node_stack = [None, None, None, None]

    for line in file_text:
        line_parsed = m_re.match(line.strip())
        if not line_parsed:
            continue
        next_record = (int(line_parsed.group(1)),
                       line_parsed.group(2), line_parsed.group(3))
        if prev_record:
            AddNewNode(node_stack, prev_record, next_record)
            if next_record[0] == 0:
                root.append(node_stack[0])
        prev_record = next_record

    if prev_record:
        AddNewNode(node_stack, prev_record, next_record)
        root.append(node_stack[0])

    xml_tree = ET.ElementTree(root)
    xml_tree.write(output_file)


if __name__ == '__main__':
    if(len(sys.argv) != 3):
        sys.exit(print('Usage: %s <gedcom_file> <xml_file>' % sys.argv[0]))
    gedcom_parser(sys.argv[1], sys.argv[2])
