/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.twit.webcrawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author root
 */
public class Consumer {

    List<Produto> produtos = new ArrayList();

    int code = 1;
    int codeAnterior = 1;

    public void start() {
        crawl();
    }

    public void crawl() {

        String urlSite = "http://www.magazineluiza.com.br/";

        vasculharPrecoErradoHref(urlSite);
//        HashSet<String> anchorsHref = new HashSet<String>();
        //
        //        try {
        //            Document doc = Jsoup.connect(urlSite).get();
        //            Elements hrefs = doc.select("a");
        //
        //            for (Element anchor : hrefs) {
        //
        //                String a = anchor.attr("href").trim();
        //
        //                if (!a.startsWith("http")) {
        //                    a = urlSite + a;
        //                }
        //
        //                anchorsHref.add(a);
        //            }
        //
        //            for (String s : anchorsHref) {
        //                System.out.println(s);
        //            }
        //
        //        } catch (IOException ex) {
        //            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        //        }
        //
        //        for (String anchorsHref1 : anchorsHref) {
        //
        //            vasculharPrecoErradoHref(anchorsHref1);
        //
        //        }
    }
//

    public void vasculharPrecoErradoHref(String href) {

        try {
            Document doc = Jsoup.connect(href).get();
            Elements hrefs = doc.select("span");

            Produto produto;

            for (Element anchor : hrefs) {

                String a = anchor.attr("class").trim();

                switch (a) {
                    case "productTitle":
                        produto = new Produto();
                        produto.setCode(code);
                        produto.setNome(anchor.text());

                        code++;
                        codeAnterior = code - 1;

                        produtos.add(produto);

                        break;
                    case "originalPrice":
                        String x = anchor.text().trim();

                        String[] dadosOrigPrice = x.split(" ");
                        int pos = dadosOrigPrice.length - 1;
                        String dadoTrat = dadosOrigPrice[pos].replace(".", "");
                        String oriPriceStr = dadoTrat.replace(",", ".");
                        double origPrice = Double.parseDouble(oriPriceStr);

                        produto = getProduto(codeAnterior);
                        produto.setOriginalPrice(origPrice);
                        break;
                    case "price":
                        produto = getProduto(codeAnterior);

                        String[] dadosPrice = anchor.text().split(" ");
                        int pos2 = dadosPrice.length - 1;
                        String dadoTrat2 = dadosPrice[pos2].replace(".", "");

                        String priceStr = dadoTrat2.replace(",", ".");
                        double price = Double.parseDouble(priceStr);

                        produto.setPrice(price);

                        if (produto.getOriginalPrice() > 0) {

                            double percent = 0;
                            double origPr = produto.getOriginalPrice();
                            double pr = produto.getPrice();

                            percent = (pr * 100) / origPr;
                            produto.setPercent(percent);
                        } else {
                            produto.setPercent(100);
                        }

                        break;
                }
            }

            for (Produto p : produtos) {
                System.out.println(p.toString());
            }

        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Produto getProduto(int code) {
        Produto prod = null;
        for (Produto produto : produtos) {

            if (produto.getCode() == code) {
                prod = produto;
                break;
            }
        }

        return prod;
    }
}

class Produto {

    private int code;
    private String nome;
    private double originalPrice;
    private double price;
    private double percent;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Produto{" + "code=" + code + ", nome=" + nome + ", originalPrice=" + originalPrice + ", price=" + price + ", percent=" + percent + '}';
    }
}
