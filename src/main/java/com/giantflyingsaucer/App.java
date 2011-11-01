package com.giantflyingsaucer;

import java.util.Date;

import net._01001111.text.LoremIpsum;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.protocol.client.AbderaClient;
import org.apache.abdera.protocol.client.ClientResponse;
import org.apache.abdera.protocol.client.RequestOptions;

/*
 * This demo client is used to simply
 * add random ATOM entries to an ATOMPub
 * server.
 */
public class App {

    @SuppressWarnings("SleepWhileInLoop")
    public static void main(String[] args) throws Exception {

        Abdera abdera = new Abdera();
        Factory factory = abdera.getFactory();

        RequestOptions opts = new RequestOptions();
        opts.setContentType("application/atom+xml;type=entry");

        LoremIpsum loremIpsum = new LoremIpsum();
        
        // Add 50 entries pausing for 3 seconds each time
        for (int i = 0; i < 50; i++) {
            Entry entry = factory.newEntry();
            entry.setTitle(loremIpsum.sentence());
            entry.setUpdated(new Date());
            entry.addAuthor(loremIpsum.words(2));
            report("The ATOM Entry for POSTing", entry.toString());
            
            AbderaClient abderaClient = new AbderaClient(abdera);
            ClientResponse resp = abderaClient.post("http://192.168.0.104:8080/ah-war-0.9.3-SNAPSHOT/namespace/feed/", entry, opts);
            report("HTTP STATUS TEXT", resp.getStatusText());
            Thread.sleep(3000);
        }
    }

    private static void report(String title, String message) {
        System.out.println("== " + title + " ==");
        if (message != null) {
            System.out.println(message);
        }
        System.out.println();
    }
}
