import xml.etree.ElementTree as ET

# Parse the XML file
tree = ET.parse('my_file.xml')

# Get the root element
root = tree.getroot()

# Write the XML file to a new file
with open('new_file.xml', 'w') as f:
    f.write(ET.tostring(root, pretty_print=True))
