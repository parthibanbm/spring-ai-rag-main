package com.parthi.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RAGSampleDataLoader {

    private final VectorStore vectorStore;

    public RAGSampleDataLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    //@PostConstruct
    public void loadSampleDataToVectorStore() {

        List<String> sampleData = getSampleData();
        // chunks -> embedding -> vector store
        List<Document> documents = sampleData.stream()
                .map(Document::new)
                .toList();

        vectorStore.add(documents);

    }


    private List<String> getSampleData() {
        return List.of(
                // Technology
                "Java is a popular programming language used to build enterprise applications and backend systems.",
                "Spring Boot simplifies Java application development by providing auto configuration and embedded servers.",
                "Docker allows developers to package applications and dependencies into containers.",
                "Kubernetes is used to orchestrate and manage containerized applications at scale.",
                "Vector databases store embeddings which allow semantic search using similarity instead of exact keywords.",

                // Share Market
                "The stock market is a place where investors buy and sell shares of publicly listed companies.",
                "The Nifty 50 is a benchmark stock market index representing 50 major companies listed on the National Stock Exchange of India.",
                "The Sensex is an index of 30 large companies listed on the Bombay Stock Exchange.",
                "Bull market refers to a period when stock prices are rising or expected to rise.",
                "Bear market refers to a period when stock prices are falling or expected to fall.",
                "Investors often diversify their portfolios to reduce risk in the stock market.",
                "Long term investing in fundamentally strong companies can generate significant wealth over time.",

                // Startup Ecosystem
                "A startup is a young company founded to develop a unique product or service and bring it to market.",
                "India has one of the largest startup ecosystems in the world with companies like Flipkart, Zomato and Paytm.",
                "Venture capital firms invest in startups with high growth potential.",
                "Unicorn startups are companies valued at over one billion dollars.",
                "Bangalore is known as the startup capital of India.",

                // War / Global Situation
                "Geopolitical tensions between countries can impact global markets and trade.",
                "Wars often disrupt supply chains and increase commodity prices such as oil and gas.",
                "International organizations like the United Nations work to maintain peace and security worldwide.",
                "Defense budgets often increase during periods of geopolitical conflict.",
                "Global conflicts can affect economic growth and financial stability.",

                // Cricket
                "Cricket is one of the most popular sports in India.",
                "The International Cricket Council or ICC governs international cricket tournaments.",
                "India won the ICC Cricket World Cup in 1983 and 2011.",
                "Sachin Tendulkar is considered one of the greatest cricketers in history.",
                "Virat Kohli is known for his aggressive batting style and leadership.",
                "The Indian Premier League or IPL is one of the richest cricket leagues in the world.",
                "MS Dhoni led India to victory in the 2007 T20 World Cup and the 2011 ODI World Cup.",

                // Economy
                "Inflation refers to the increase in prices of goods and services over time.",
                "Central banks raise interest rates to control inflation.",
                "Gross Domestic Product or GDP measures the total economic output of a country.",
                "Economic growth is often driven by innovation, investments and consumer spending.",

                // General Knowledge
                "Artificial Intelligence enables machines to simulate human intelligence and decision making.",
                "Machine learning is a subset of artificial intelligence where systems learn from data.",
                "Cloud computing allows applications to run on remote servers instead of local machines.",
                "Cybersecurity protects systems and data from digital attacks.",
                "Renewable energy sources include solar, wind and hydroelectric power.",

                // Youtube - javaTechie
                "Java Techie is a YouTube channel focused on Java, Spring Boot, Microservices, and System Design tutorials.",
                "Java Techie provides practical tutorials on Kafka, Kubernetes, gRPC, and Spring Boot.",
                "The Java Techie channel helps developers prepare for technical interviews and learn backend development.",
                "Basant is the creator of Java Techie YouTube channel."
        );


    }

}
