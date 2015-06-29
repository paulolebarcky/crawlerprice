package com.twit.webcrawler;

import com.twit.controller.CrawlerController;
import com.twit.controller.ProdutoController;
import com.twit.entity.Crawler;
import com.twit.entity.Produto;
import com.twit.model.ProdutoModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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

    List<ProdutoModel> produtos = new ArrayList();

    int code = 1;
    int codeAnterior = 1;

    public void start() {
        crawl();
    }

    public void crawl() {

        String urlSite = "http://www.magazineluiza.com.br/";

        //vasculharPrecoErradoHref(urlSite);
        HashSet<String> anchorsHref = new HashSet<String>();

        try {
            Document doc = Jsoup.connect(urlSite).get();
            Elements hrefs = doc.select("a");

            for (Element anchor : hrefs) {

                String a = anchor.attr("href").trim();

                if (!a.startsWith("http")) {
                    a = urlSite + a;
                }

                anchorsHref.add(a);
            }

            for (String s : anchorsHref) {
                System.out.println(s);
            }

        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (String anchorsHref1 : anchorsHref) {

            vasculharPrecoErradoHref(anchorsHref1);

        }
    }
//

    public void vasculharPrecoErradoHref(String href) {

        try {
            Document doc = Jsoup.connect(href).get();
            Elements hrefs = doc.select("span");

            ProdutoModel produto;

            for (Element anchor : hrefs) {

                String a = anchor.attr("class").trim();

                switch (a) {

                    case "productTitle":
                        produto = new ProdutoModel();
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

            Crawler crawler = new Crawler();
            crawler.setDataCriacao(new Date());
            crawler.setPath(href);
            CrawlerController crawlerController = new CrawlerController(crawler);

            try {
                crawler = crawlerController.create();
            } catch (Exception ex) {
                Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (ProdutoModel p : produtos) {
                System.out.println(p.toString());

                Produto produtoNew = new Produto();
                produtoNew.setIdcrawler(crawler);
                produtoNew.setNome(p.getNome());
                produtoNew.setPrecooriginal(p.getOriginalPrice());
                produtoNew.setPreco(p.getPrice());
                produtoNew.setPercentual(p.getPercent());

                ProdutoController produtoController = new ProdutoController(produtoNew);
                try {
                    produtoController.create();
                } catch (Exception ex) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProdutoModel getProduto(int code) {
        ProdutoModel prod = null;
        for (ProdutoModel produto : produtos) {

            if (produto.getCode() == code) {
                prod = produto;
                break;
            }
        }

        return prod;
    }
}
