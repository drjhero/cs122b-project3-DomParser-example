import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class DomParserExample {

    void runExample() throws IOException, ParserConfigurationException, SAXException {
        Document document = parseXmlFile();
        List<Employee> employeeList = convertDocumentIntoEmployees(document);
        printEmployeeData(employeeList);
    }

    private Document parseXmlFile() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        // Create an object that can build a DOM from an XML file
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        // Parses the XML file and creates a DOM
        return documentBuilder.parse("employees.xml");
    }

    private List<Employee> convertDocumentIntoEmployees(Document document) {
        Element rootDocumentElement = document.getDocumentElement();
        List<Employee> employeeList = new ArrayList<>();
        // get a nodelist of employee Elements, parse each into Employee object
        NodeList nodeList = rootDocumentElement.getElementsByTagName("Employee");
        for (int i = 0; i < nodeList.getLength(); i++) {
            // Get the employee DOM element
            Element element = (Element) nodeList.item(i);
            Employee employee = convertDomElementToEmployee(element);
            employeeList.add(employee);
        }
        return employeeList;
    }

    private Employee convertDomElementToEmployee(Element element) {
        String name = getTextValue(element, "Name");
        String type = element.getAttribute("type");
        int id = getIntValue(element, "Id");
        int age = getIntValue(element, "Age");
        return new Employee(name, id, age, type);
    }

    private String getTextValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            // here we expect only one <Name> would present in the <Employee>
            return nodeList.item(0).getFirstChild().getNodeValue();
        }
        return null;
    }

    private int getIntValue(Element ele, String tagName) {
        return Integer.parseInt(getTextValue(ele, tagName));
    }

    private void printEmployeeData(List<Employee> employeeList) {
        System.out.println("Total parsed " + employeeList.size() + " employees");
        for (Employee employee : employeeList) {
            System.out.println("\t" + employee.toString());
        }
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        DomParserExample domParserExample = new DomParserExample();
        domParserExample.runExample();
    }
}
