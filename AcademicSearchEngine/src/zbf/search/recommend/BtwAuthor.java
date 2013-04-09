package zbf.search.recommend;

import java.io.IOException;
import java.util.ArrayList;

import zbf.search.mongodb.MyMongoClient;
import zbf.search.util.StdOutUtil;
import zbf.search.util.StringUtil;

import com.mongodb.BasicDBList;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class BtwAuthor {
	public static void main(String[] args) throws IOException {
		MyMongoClient mongoClient = new MyMongoClient("researchers");
		DBCollection coll = mongoClient.getDBCollection();

		ArrayList<String> doc1 = new ArrayList<String>();
		ArrayList<String> doc2 = new ArrayList<String>();

		DBObject target = coll.findOne();
		BasicDBList tmp = (BasicDBList) target.get("field");
		String target_field = StringUtil.filterAmp(tmp.toString());
		StdOutUtil.out(target.get("name"));
		StdOutUtil.out(target_field);
		doc1 = StringUtil.getTokens(target_field);

		DBCursor cursor = coll.find();
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();

			BasicDBList fields = (BasicDBList) obj.get("field");
			String field = StringUtil.filterAmp(fields.toString());
			if (field.length() > 3) {
				//StdOutUtil.out(field);
				doc2 = StringUtil.getTokens(field);
				double sim = CosineDis.getSimilarity(doc1, doc2);
				if (sim == 1.0) {
					StdOutUtil.out(sim);
				}
			}
		}
	}
}