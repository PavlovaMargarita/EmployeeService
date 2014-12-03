package com.itechart.service.impl;

import com.itechart.model.mongo.Vacancy;
import com.itechart.params.SecurityWrapper;
import com.itechart.service.VacancyService;
import com.mongodb.*;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {
    @Override
    public List<Vacancy> readVacancyList(int pageNumber, int pageRecords) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        DBCursor cursorVacancyIdList = null;
        DBCursor cursorVacancyList = null;
        List<Vacancy> vacancyList = new ArrayList<>();
        try {
            List<Object> vacancyIdList = new ArrayList<>();

            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionCompanyOpenVacancy = database.getCollection("company_open_vacancy");

            DBObject querySearchByCompanyId = new BasicDBObject();
            querySearchByCompanyId.put("_id", SecurityWrapper.getCurrentCompanyId());

            cursorVacancyIdList = collectionCompanyOpenVacancy.find(querySearchByCompanyId);

            if (cursorVacancyIdList != null && cursorVacancyIdList.hasNext()) {
                DBObject companyObject = cursorVacancyIdList.next();

                BasicDBList vacancyIdListObject = (BasicDBList) companyObject.get("vacancy_id_list");

                for(Object o: vacancyIdListObject){
                    vacancyIdList.add(((BasicDBObject)o).get("_id"));
                }
            }
            DBCollection vacancyCollection = database.getCollection("vacancy");
            DBObject querySearchVacancyByIdList = new BasicDBObject();
            querySearchVacancyByIdList.put("_id", new BasicDBObject("$in", vacancyIdList));

            cursorVacancyList = vacancyCollection.find(querySearchVacancyByIdList).skip((pageNumber - 1) * pageRecords).limit(pageRecords);

            while (cursorVacancyList.hasNext()) {
                DBObject vacancyMongoObject = cursorVacancyList.next();
                Vacancy vacancy = new Vacancy();
                vacancy.setId(Long.parseLong(vacancyMongoObject.get("_id").toString()));
                vacancy.setDescription(vacancyMongoObject.get("description").toString());
                vacancyList.add(vacancy);
            }
        } finally {
            mongoClient.close();
            if (cursorVacancyIdList != null)
                cursorVacancyIdList.close();
            if (cursorVacancyList != null) {
                cursorVacancyList.close();
            }
        }
        return vacancyList;
    }

    @Override
    public void createVacancy(Vacancy vacancy) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        try {
            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionVacancy = database.getCollection("vacancy");

            DBObject vacancyMongoObject = new BasicDBObject();
            long lastId = getLastIdWithIncrement(database);
            vacancyMongoObject.put("_id", lastId);
            vacancyMongoObject.put("description", vacancy.getDescription());
            vacancyMongoObject.put("companyId", SecurityWrapper.getCurrentCompanyId());

            collectionVacancy.insert(vacancyMongoObject);

            DBCollection collectionCompanyOpenVacancy = database.getCollection("company_open_vacancy");

            DBObject queryCompanyId = new BasicDBObject();
            queryCompanyId.put("_id", SecurityWrapper.getCurrentCompanyId());

            if (!collectionCompanyOpenVacancy.find(queryCompanyId).hasNext()) {
                DBObject insertCompanyObject = new BasicDBObject();

                insertCompanyObject.put("_id", SecurityWrapper.getCurrentCompanyId());

                BasicDBList vacancyIdListObject = new BasicDBList();
                vacancyIdListObject.add(new BasicDBObject("_id", lastId));

                insertCompanyObject.put("vacancy_id_list", vacancyIdListObject);

                collectionCompanyOpenVacancy.insert(insertCompanyObject);
            } else {
                DBObject vacancyIdObject = new BasicDBObject("_id", lastId);
                DBObject vacancyIdListObject = new BasicDBObject();
                vacancyIdListObject.put("vacancy_id_list", vacancyIdObject);

                DBObject updateObject = new BasicDBObject("$push", vacancyIdListObject);

                collectionCompanyOpenVacancy.update(queryCompanyId, updateObject);
            }
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public void updateVacancy(Vacancy vacancy) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        try {
            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionVacancy = database.getCollection("vacancy");

            DBObject querySearchByVacancyId = new BasicDBObject();
            querySearchByVacancyId.put("_id", vacancy.getId());

            DBCursor cursor = collectionVacancy.find(querySearchByVacancyId);

            if (cursor.hasNext()) {
                DBObject vacancyObject = cursor.next();
                vacancyObject.put("description", vacancy.getDescription());
                collectionVacancy.save(vacancyObject);
            }
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public Vacancy readVacancyById(long id) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        Vacancy vacancy = new Vacancy();
        try {
            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionVacancy = database.getCollection("vacancy");

            BasicDBObject querySearchByVacancyId = new BasicDBObject();
            querySearchByVacancyId.put("_id", id);

            DBCursor cursor = collectionVacancy.find(querySearchByVacancyId);

            if (cursor.hasNext()) {
                DBObject vacancyObject = cursor.next();
                vacancy.setId(Long.parseLong(vacancyObject.get("_id").toString()));
                vacancy.setDescription(vacancyObject.get("description").toString());
            }

        } finally {
            mongoClient.close();
        }
        return vacancy;
    }

    @Override
    public void deleteVacancyById(long id) throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        try {
            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionVacancy = database.getCollection("vacancy");
            BasicDBObject vacancyObject = new BasicDBObject();

            java.util.Date utilDate = new java.util.Date();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            vacancyObject.append("$set", new BasicDBObject().append("dateClose", sqlDate));

            BasicDBObject querySearchByVacancyId = new BasicDBObject().append("_id", id);

            collectionVacancy.update(querySearchByVacancyId, vacancyObject);
            //collection.remove(vacancyMongoObject);

            //delete from open vacancy
            DBCollection collectionCompanyOpenVacancy = database.getCollection("company_open_vacancy");
            BasicDBObject querySearchByCompanyId = new BasicDBObject();
            querySearchByCompanyId.put("_id", SecurityWrapper.getCurrentCompanyId());

            vacancyObject = new BasicDBObject("_id", id);
            BasicDBObject vacancyIdListObject = new BasicDBObject();
            vacancyIdListObject.append("vacancy_id_list", vacancyObject);

            DBObject updateInCompanyOpenVacancyObject = new BasicDBObject("$pull", vacancyIdListObject);
            collectionCompanyOpenVacancy.update(querySearchByCompanyId, updateInCompanyOpenVacancyObject);

        } finally {
            mongoClient.close();
        }
    }
//
//    @Override
//    public void removeAll() throws UnknownHostException {
//        MongoClient mongoClient = new MongoClient("localhost", 27017);
//        try {
//            DB database = mongoClient.getDB("employee_service");
//            DBCollection collection = database.getCollection("vacancy");
//            collection.remove(new BasicDBObject());
//        } finally {
//            mongoClient.close();
//        }
//    }

    @Override
    public long vacancyCount() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        long count;
        DBCursor cursorVacancyIdList = null;
        try {
            DB database = mongoClient.getDB("employee_service");
            DBCollection collectionCompanyOpenVacancy = database.getCollection("company_open_vacancy");

            DBObject querySearchByCompanyId = new BasicDBObject();
            querySearchByCompanyId.put("_id", SecurityWrapper.getCurrentCompanyId());

            cursorVacancyIdList = collectionCompanyOpenVacancy.find(querySearchByCompanyId);

            if (cursorVacancyIdList.hasNext()) {
                DBObject companyObject = cursorVacancyIdList.next();

                BasicDBList vacancyIdListObject = (BasicDBList) companyObject.get("vacancy_id_list");
                count = vacancyIdListObject.size();
            } else {
                count = 0;
            }
        } finally {
            mongoClient.close();
            if(cursorVacancyIdList != null){
                cursorVacancyIdList.close();
            }
        }
        return count;

    }

    private long getLastIdWithIncrement(DB database) {
        DBCollection collection = database.getCollection("last_vacancy_id");
        DBCursor cursor = collection.find();
        if (cursor.hasNext()) {
            DBObject lastIdMongoObject = cursor.next();
            DBObject modifier = new BasicDBObject("last_id", 1);
            DBObject incQuery = new BasicDBObject("$inc", modifier);
            DBObject result = collection.findAndModify(lastIdMongoObject,(DBObject)null, (DBObject)null, false, incQuery,true, false);
//            System.out.println(test);
            long lastId = Long.parseLong(result.get("last_id").toString());
            return lastId;
        } else {
            DBObject lastIdMongoObject = new BasicDBObject();
            long lastId = 1;
            lastIdMongoObject.put("last_id", lastId);
            collection.insert(lastIdMongoObject);
            return lastId;
        }
    }
}
