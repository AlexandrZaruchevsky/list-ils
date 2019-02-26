package ru.zaa.pfr.xml.ils.list;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Application {
    public static void main(String[] args) throws IOException {

        List<File> fileList = Files.walk(Paths.get(args[0]))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .filter(p -> p.toString().toUpperCase().contains(".XML"))
                .collect(Collectors.toList());

        System.out.println(fileList.size());

        System.out.println(new Date());

        Map<String, Person> persons = new HashMap<>();

        for (File ff : fileList) {

            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(ff);

                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("СВЕДЕНИЯ_О_СТРАХОВЫХ_ВЗНОСАХ_И_СТРАХОВОМ_СТАЖЕ_ЗЛ");
                if (nodeList.getLength() == 0) {
                    nodeList = doc.getElementsByTagName("СВЕДЕНИЯ_О_СУММЕ_ВЫПЛАТ_И_СТРАХОВОМ_СТАЖЕ_ЗЛ");
                }
                if (nodeList.getLength() == 0) {
                    nodeList = doc.getElementsByTagName("СВЕДЕНИЯ_О_СУММЕ_ВЫПЛАТ_О_СТРАХОВЫХ_ВЗНОСАХ_И_СТРАХОВОМ_СТАЖЕ_ЗЛ");
                }

                if (nodeList.getLength() != 0) {
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Person person = new Person();
                        Node node = nodeList.item(i);
                        if (Node.ELEMENT_NODE == node.getNodeType()) {
                            Element element = (Element) node;
                            String snils = element.getElementsByTagName("СтраховойНомер").item(0).getTextContent().trim();
                            person.setSnils(snils);
                            NodeList nodeList1 = element.getElementsByTagName("ФИО");
                            Node fio = nodeList1.item(0);
                            if (Node.ELEMENT_NODE == fio.getNodeType()) {
                                Element element1 = (Element) fio;
                                person.setLastName(element.getElementsByTagName("Фамилия").item(0).getTextContent().trim());
                                person.setFirstName(element1.getElementsByTagName("Имя").item(0).getTextContent().trim());//                    System.out.println(element1.getElementsByTagName("Имя").item(0).getTextContent());
                                person.setMidleName(element1.getElementsByTagName("Отчество").item(0).getTextContent().trim());
                            }
                            persons.put(snils, person);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Ошибка обработки файла " + ff);
                System.out.println(ff.toString());
            }
        }
        System.out.println(persons.size());
        try (FileWriter writer = new FileWriter("list.csv")) {
            List<String> lp = new ArrayList<>(persons.keySet());
            Collections.sort(lp);
            for (int i = 0; i < lp.size(); i++) {
                Person person = persons.get(lp.get(i));
                String line = (i + 1) + ";" +
                        person.getSnils() +";"+
                        person.getLastName() +";"+
                        person.getFirstName() +";"+
                        person.getMidleName() +";\n";
                writer.write(line);
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(new Date());
    }
}
