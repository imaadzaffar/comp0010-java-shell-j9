import xml.etree.ElementTree as ET

xml_fname = "target/pmd.xml"
tree = ET.parse(xml_fname)
root = tree.getroot()

for f in root:
    print("\n----")
    print(f.attrib["name"])
    print("----")
    
    for v in f:
        print("\nRule:", v.attrib["rule"])
        print("- Priority:", v.attrib["priority"])
        print("- Message:", v.text.strip())