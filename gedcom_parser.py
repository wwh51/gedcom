import re
import xml.etree.ElementTree as ET


def create_node_by_line(line, hasChild = False):
	if line[2] is None:
		return ET.Element(line[1])	
	name = line[1]
	id_pattern = re.compile(r'(@.*@)')
	node_id = id_pattern.match(line[1])
	if node_id:
		node_id = node_id.group(1)
		name = line[2].strip()
	else:
		node_id = id_pattern.match(line[2].strip())
		if node_id:
			node_id = node_id.group(1)
	new_node = ET.Element(name.lower())
	if node_id:
		new_node.set('id', node_id)
	elif hasChild:
		new_node.set('value', line[2])
	else:
		new_node.text = line[2]
	return new_node



with open('t.txt', 'rt') as f:
    file_text = f.read()
file_text = file_text.strip('\n').split('\n')

root=ET.Element('gedcom')
m_re = re.compile(r'\s*(\d+)\s+(\S+)(\s+.*)*')
prev_record = None
node_stack=[]

for line in file_text:   
    line_parsed = m_re.match(line.strip())
    if not line_parsed:
    	continue
    new_record = (int(line_parsed.group(1)), line_parsed.group(2), line_parsed.group(3))
    if prev_record:
    	hasChild = prev_record[0] < new_record[0]    	
    	prev_node = create_node_by_line(prev_record, hasChild)
    	if node_stack:
    		node_stack[-1].append(prev_node)
    		if new_record[0] == 0:
    			root.append(node_stack[0])
    			prev_record = None
    			node_stack = []
    		elif hasChild:
    			node_stack.append(prev_node)
    		elif prev_record[0] > new_record[0]:
    			node_stack.pop()
    	else:
    		node_stack.append(prev_node)
    prev_record = new_record

if prev_record:
	root.append(ET.Element(prev_record[1].lower()))
xml_tree = ET.ElementTree(root)
xml_tree.write('out.xml')
