import sys
import re
import xml.etree.ElementTree as ET


def create_node_by_line(line):
    if line[2] is None:
        return ET.Element(line[1].lower())
    name = line[1]
    value = line[2].strip()
    id_pattern = re.compile(r'(@.*@)')
    node_id = id_pattern.match(name)  # the first one may be id
    if node_id:
        node_id = node_id.group(1)
        name = value
    else:
        node_id = id_pattern.match(value)  # the second one is id
        if node_id:
            node_id = node_id.group(1)
    new_node = ET.Element(name.lower())
    if node_id:
        new_node.set('id', node_id)
    else:
        new_node.text = value
    return new_node


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

    Element_stack = [(-1, root)]

    for line in file_text:
        line_parsed = m_re.match(line.strip())
        if not line_parsed:
            continue
        new_record = (int(line_parsed.group(1)),
                       line_parsed.group(2), line_parsed.group(3))
        new_Element = create_node_by_line(new_record)
        
        if new_record[0] > Element_stack[-1][0] and Element_stack[-1][1].text is not None:
            Element_stack[-1][1].set('value', Element_stack[-1][1].text)
            Element_stack[-1][1].text = None

        while new_record[0] <= Element_stack[-1][0]:
            Element_stack.pop()
        Element_stack[-1][1].append(new_Element)
        Element_stack.append((new_record[0], new_Element))

    xml_tree = ET.ElementTree(root)
    try:
        xml_tree.write(output_file)
    except:
        print('Error: Writing xml file failed')
        return

if __name__ == '__main__':
    if(len(sys.argv) != 3):
        sys.exit(print('Usage: %s <gedcom_file> <xml_file>' % sys.argv[0]))
    gedcom_parser(sys.argv[1], sys.argv[2])
