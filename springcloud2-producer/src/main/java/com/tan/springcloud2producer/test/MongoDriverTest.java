package com.tan.springcloud2producer.test;

import com.mongodb.Block;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.match;
import static com.mongodb.client.model.Aggregates.project;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class MongoDriverTest {
    /**
     * Run this main method to see the output of this quick example.
     *
     * @param args takes an optional single argument for the connection string
     */
    public static void main(final String[] args) {
        MongoClient mongoClient;
        if (args.length == 0) {
            // connect to the local database server
            mongoClient = MongoClients.create();
        } else {
            mongoClient = MongoClients.create(args[0]);
        }

        // get handle to "mydb" database
        MongoDatabase database = mongoClient.getDatabase("mydb");


        // get a handle to the "test" collection
        MongoCollection<Document> collection = database.getCollection("test");

        if (collection.countDocuments() != 0){
            // drop all the data in it
            collection.drop();
        }

        // make a document and insert it
        Document doc = new Document("name", "MongoDB")
                .append("type", "database")
                .append("count", 1)
                .append("info", new Document("x", 203).append("y", 102));

        collection.insertOne(doc);

        // get it (since it's the only one in there since we dropped the rest earlier on)
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());

        //获取开始时间
        long startTime = System.currentTimeMillis();

        // now, lets add lots of little documents to the collection so we can explore queries and cursors
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < 10000; i++) {
            documents.add(new Document("i", i));
        }
        collection.insertMany(documents);
        //获取结束时间
        long endTime = System.currentTimeMillis();
        //输出程序运行时间
        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");


        System.out.println("total # of documents after inserting 100 small ones (should be 101) " + collection.countDocuments());

        // find first
        myDoc = collection.find().first();
        System.out.println(myDoc.toJson());

        // lets get all the documents in the collection and print them out
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        for (Document cur : collection.find()) {
            System.out.println(cur.toJson());
        }

        // now use a query to get 1 document out
        myDoc = collection.find(eq("i", 71)).first();
        System.out.println(myDoc.toJson());

        // now use a range query to get a larger subset
        cursor = collection.find(gt("i", 50)).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        // range query with multiple constraints
        cursor = collection.find(and(gt("i", 50), lte("i", 100))).iterator();

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }

        // Query Filters
        myDoc = collection.find(eq("i", 71)).first();
        System.out.println(myDoc.toJson());

        // now use a range query to get a larger subset
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                System.out.println(document.toJson());
            }
        };
        collection.find(gt("i", 50)).forEach(printBlock);

        // filter where; 50 < i <= 100
        collection.find(and(gt("i", 50), lte("i", 100))).forEach(printBlock);

        // Sorting
        myDoc = collection.find(exists("i")).sort(descending("i")).first();
        System.out.println(myDoc.toJson());

        // Projection
        myDoc = collection.find().projection(excludeId()).first();
        System.out.println(myDoc.toJson());


        // Aggregation
        collection.aggregate(asList( match(gt("i", 0)), project(Document.parse("{ITimes10: {$multiply: ['$i', 10]}}")))
        ).forEach(printBlock);

        myDoc = collection.aggregate(singletonList(group(null, sum("total", "$i")))).first();
        System.out.println(myDoc.toJson());

        // Update One
        collection.updateOne(eq("i", 10), set("i", 110));

        // Update Many
        UpdateResult updateResult = collection.updateMany(lt("i", 100), inc("i", 100));
        System.out.println(updateResult.getModifiedCount());

        // Delete One
        collection.deleteOne(eq("i", 110));

        // Delete Many
//        DeleteResult deleteResult = collection.deleteMany(gte("i", 100));
//        System.out.println(deleteResult.getDeletedCount());

//        collection.drop();

        /*
        // ordered bulk writes
        List<WriteModel<Document>> writes = new ArrayList<WriteModel<Document>>();
        writes.add(new InsertOneModel<Document>(new Document("_id", 4)));
        writes.add(new InsertOneModel<Document>(new Document("_id", 5)));
        writes.add(new InsertOneModel<Document>(new Document("_id", 6)));
        writes.add(new UpdateOneModel<Document>(new Document("_id", 1), new Document("$set", new Document("x", 2))));
        writes.add(new DeleteOneModel<Document>(new Document("_id", 2)));
        writes.add(new ReplaceOneModel<Document>(new Document("_id", 3), new Document("_id", 3).append("x", 4)));

        collection.bulkWrite(writes);
*/
//        collection.drop();

//        collection.bulkWrite(writes, new BulkWriteOptions().ordered(false));
        //collection.find().forEach(printBlock);

        // Clean up
//        database.drop();

        // release resources
        mongoClient.close();
    }
}
