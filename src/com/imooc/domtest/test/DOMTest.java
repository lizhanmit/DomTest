package com.imooc.domtest.test;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DOMTest {

	public DocumentBuilder getDocumentBuilder() {
		//����һ��DocumentBuilderFactory�Ķ���
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		//����DocumentBuilder����
		DocumentBuilder db = null;
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return db;
	}
	
	/**
	 * ����xml�ļ�
	 */
	public void xmlParser() {
		try {
			//ͨ��DocumentBuilder�����parser��������books.xml�ļ�����ǰ��Ŀ��
			Document document = getDocumentBuilder().parse("books.xml");
			//��ȡ����book�ڵ�ļ���
			NodeList bookList = document.getElementsByTagName("book");
			//ͨ��nodelist��getLength()�������Ի�ȡbookList�ĳ���
			System.out.println("һ����" + bookList.getLength() + "����");
			//����ÿһ��book�ڵ�
			for (int i = 0; i < bookList.getLength(); i++) {
				System.out.println("=================���濪ʼ������" + (i + 1) + "���������=================");
				//ͨ�� item(i)���� ��ȡһ��book�ڵ㣬nodelist������ֵ��0��ʼ
				Node book = bookList.item(i);
//						��ȡbook�ڵ���������Լ���
				NamedNodeMap attrs = book.getAttributes();
				System.out.println("�� " + (i + 1) + "���鹲��" + attrs.getLength() + "������");
//						����book������
				for (int j = 0; j < attrs.getLength(); j++) {
					//ͨ��item(index)������ȡbook�ڵ��ĳһ������
					Node attr = attrs.item(j);
					//��ȡ������
					System.out.print("��������" + attr.getNodeName());
					//��ȡ����ֵ
					System.out.println("--����ֵ" + attr.getNodeValue());
				}
//						//ǰ�᣺�Ѿ�֪��book�ڵ�����ֻ����1��id����
//						//��book�ڵ����ǿ������ת����ת����Element����
//						Element book = (Element) bookList.item(i);
//						//ͨ��getAttribute("id")������ȡ����ֵ
//						String attrValue = book.getAttribute("id");
//						System.out.println("id���Ե�����ֵΪ" + attrValue);
				//����book�ڵ���ӽڵ�
				NodeList childNodes = book.getChildNodes();
				//����childNodes��ȡÿ���ڵ�Ľڵ����ͽڵ�ֵ
				System.out.println("��" + (i+1) + "���鹲��" + 
				childNodes.getLength() + "���ӽڵ�");
				for (int k = 0; k < childNodes.getLength(); k++) {
					//���ֳ�text���͵�node�Լ�element���͵�node
					if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
						//��ȡ��element���ͽڵ�Ľڵ���
						System.out.print("��" + (k + 1) + "���ڵ�Ľڵ�����" 
						+ childNodes.item(k).getNodeName());
//								��ȡ��element���ͽڵ�Ľڵ�ֵ
						System.out.println("--�ڵ�ֵ�ǣ�" + childNodes.item(k).getFirstChild().getNodeValue());
//								System.out.println("--�ڵ�ֵ�ǣ�" + childNodes.item(k).getTextContent());
					}
				}
				System.out.println("======================����������" + (i + 1) + "���������=================");
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����xml�ļ�
	 */
	public void createXML() {
		DocumentBuilder db = getDocumentBuilder();
		// ͨ��DocumentBuilder�����newDocument��������xml�ļ�
		Document document = db.newDocument();
		// ����xml�ļ���standalone����Ϊtrue����Ϊyes����ʾ
		document.setXmlStandalone(true);
		// �������ڵ�
		Element bookstore = document.createElement("bookStore");
		// �����ӽڵ�
		Element book = document.createElement("book");
		// ���������ӽڵ�
		Element name = document.createElement("name");
		Element author = document.createElement("author");
		Element year = document.createElement("year");
		Element price = document.createElement("price");
		// д������ӽڵ��ֵ
		name.setTextContent("С����");
		author.setTextContent("�����򡤵¡�ʥ����������");
		year.setTextContent("1943");
		price.setTextContent("50");
		// �Ѷ����ӽڵ�name��ӵ��ӽڵ�book��
		book.appendChild(name);
		book.appendChild(author);
		book.appendChild(year);
		book.appendChild(price);
		// �����ӽڵ�������������ֵ
		book.setAttribute("id", "1");
		// ���ӽڵ�book��ӵ����ڵ�bookstore��
		bookstore.appendChild(book);
		// �Ѹ��ڵ�bookstore��ӵ�document���γ�һ��dom��
		document.appendChild(bookstore);
		
		// ��dom���ṹת����xml�ļ�
		// ����TransformerFactory����
		TransformerFactory tff = TransformerFactory.newInstance();
		try {
			// ����Transformer����
			Transformer tf = tff.newTransformer();
			// ����xml�ļ��е����ݻ���
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.transform(new DOMSource(document), new StreamResult(new File("books1.xml")));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args) {
		// ����DOMTest����
		DOMTest test = new DOMTest();
		// ���ý�������������xml�ļ�
		//test.xmlParser();
		// ����д�뷽��
		test.createXML();
		
	}
}
