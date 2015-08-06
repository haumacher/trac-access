/*
 * Copyright (c) 2005-2015, Bernhard Haumacher.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies,
 * either expressed or implied, of the FreeBSD Project.
 */
package de.haumacher.trac.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;
import java.util.Vector;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.lustin.trac.xmlprc.Ticket;
import org.lustin.trac.xmlprc.Ticket.Type;
import org.lustin.trac.xmlprc.TrackerDynamicProxy;

public class TracDemo {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		XmlRpcClientConfigImpl config = createXmlRpcConfig();
		
		XmlRpcClient client = new XmlRpcClient();
		client.setConfig(config);
		
		TrackerDynamicProxy factory = new TrackerDynamicProxy(client);
		
		Ticket tickets = (Ticket) factory.newInstance(Ticket.class);

		Vector<HashMap> fields = tickets.getTicketFields();
		System.out.println("Ticket fields: " + fields);
		
		Vector ticket = tickets.get(1);
		System.out.println("Ticket(1): " + ticket);
		
		Ticket.Type types = (Ticket.Type) factory.newInstance(Ticket.Type.class);
		Vector allTypes = types.getAll();
		System.out.println("Ticket types: " + allTypes);
		
		org.lustin.trac.xmlprc.System tracSystem = (org.lustin.trac.xmlprc.System) factory.newInstance(org.lustin.trac.xmlprc.System.class);
		Vector<String> allMethods = tracSystem.listMethods();
		System.out.println("All methods: " + allMethods);
		
		for (String method : allMethods) {
			Vector signature = tracSystem.methodSignature(method);
			System.out.println("Signature of " + method + ": " + signature);
			String methodHelp = tracSystem.methodHelp(method);
			System.out.println("Description of " + method + ": " + methodHelp);
		}
	}

	private static XmlRpcClientConfigImpl createXmlRpcConfig() throws IOException, MalformedURLException {
		Properties properties = new Properties();
		File configFile = new File("trac.properties");
		if (configFile.exists()) {
			FileInputStream configIn = new FileInputStream(configFile);
			try {
				properties.load(configIn);
			} finally {
				configIn.close();
			}
		}
		String tracUrl = properties.getProperty("trac.url");
		String user = properties.getProperty("trac.user");
		String passwd = properties.getProperty("trac.passwd");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean changed = false;
		if (tracUrl == null) {
			System.out.print("Trac URL: ");
			tracUrl = in.readLine();
			properties.setProperty("trac.url", tracUrl);
			changed = true;
		}
		if (user == null) {
			System.out.print("Trac user: ");
			user = in.readLine();
			properties.setProperty("trac.user", user);
			changed = true;
		}
		if (passwd == null) {
			System.out.print("Trac passwd: ");
			passwd = in.readLine();
			properties.setProperty("trac.passwd", passwd);
			changed = true;
		}
		if (changed) {
			FileOutputStream configOut = new FileOutputStream(configFile);
			try {
				properties.store(configOut, null);
			} finally {
				configOut.close();
			}
		}
		
		XmlRpcClientConfigImpl clientConfig = new XmlRpcClientConfigImpl();
		clientConfig.setServerURL(new URL(tracUrl));
		clientConfig.setBasicUserName(user);
		clientConfig.setBasicPassword(passwd);
		return clientConfig;
	}
	
}
