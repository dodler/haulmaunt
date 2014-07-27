/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package haulmaunt.lyan.ui;

import java.awt.Container;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.swing.JButton;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Артем
 */
public class MarkupLoader {
    
    private static final MarkupLoader markupLoaderInstance = new MarkupLoader();
    
    public static MarkupLoader getMarkupLoaderInstance(){
        return markupLoaderInstance;
    }
    
    public HashMap<String, MouseListener> mouseListeners; // пока не буду заморачиваться с инкапсуяцией
    
    private MarkupLoader(){
        mouseListeners = new HashMap<String, MouseListener>();
    }
    
    public void LoadMarkup(String name, Container parent) throws SAXException, IOException, ParserConfigurationException,MissingMouseListenerException{
        File markupSource = new File("markup.xml"); // загрузка разметки
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(markupSource);
        // TODO сделать загрузку не только кнопкок
        NodeList nodes = doc.getElementsByTagName("button"); // получили все кнопки
        JButton t;
        for (int i = 0; i < nodes.getLength(); i++) { // проходим по всем layout
            t = new JButton(nodes.item(i).getAttributes().getNamedItem("label").getTextContent());
            t.setName(nodes.item(i).getAttributes().getNamedItem("name").getTextContent());
            
            String mouseListenerName = nodes.item(i).getAttributes().getNamedItem("mouseListener").getTextContent();
            if (!mouseListenerName.equals("") && mouseListeners.containsKey(mouseListenerName)){ // ищем нужный обработчик
                t.addMouseListener(mouseListeners.get(mouseListenerName)); // если есть назначаем
            }else{
                throw new MissingMouseListenerException(); // иначе исклюние
        }
            
            t.setBounds(
                    Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("x").getTextContent()),
                    Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("y").getTextContent()),
                    Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("width").getTextContent()),
                    Integer.parseInt(nodes.item(i).getAttributes().getNamedItem("height").getTextContent())
                    );
            
            
            
            parent.add(t);
        }
    }
}
