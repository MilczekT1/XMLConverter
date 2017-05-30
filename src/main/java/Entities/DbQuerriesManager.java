package Entities;

import Main.SessionFactoryContainer;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class DbQuerriesManager {
    private static SessionFactory sessionFactory = SessionFactoryContainer.getSessionFactoryInstance();
    
    public DbQuerriesManager(){
    
    }
    
    public static boolean dbAddEntities(DataContainer XMLData){
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            
            session.save(XMLData.getKeyNumberEntity());
            session.save(XMLData.getRegisteredSENTInfoEntity());
            session.save(XMLData.getGoodsSenderEntity());
            session.save(XMLData.getGoodsRecipientEntity());
            session.save(XMLData.getPlaceOfLoadingEntity());
            session.save(XMLData.getPlaceOfDeliveryEntity());
            session.save(XMLData.getCarrierEntity());
            session.save(XMLData.getGoodsInformationEntity());
            session.save(XMLData.getMeanOfTransportEntity());
            
            session.getTransaction().commit();
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
            return false;
        }finally {
            session.close();
        }
        return true;
    }
    public static DataContainer dbExportDataUsingSENTNumber(String sentNumber){
        DataContainer exportedData = new DataContainer();
        exportedData.setKeyNumberEntity(
                (KeyNumberEntity) dbExportEntity(sentNumber,"keynumber"));
        exportedData.setRegisteredSENTInfoEntity(
                (RegisteredSENTInfoEntity) dbExportEntity(sentNumber,"registeredsentinfo"));
        exportedData.setGoodsSenderEntity(
                (GoodsSenderEntity) dbExportEntity(sentNumber,"goodssender"));
        exportedData.setGoodsRecipientEntity(
                (GoodsRecipientEntity) dbExportEntity(sentNumber,"goodsrecipient"));
        exportedData.setPlaceOfLoadingEntity(
                (PlaceOfLoadingEntity) dbExportEntity(sentNumber,"placeofloading"));
        exportedData.setPlaceOfDeliveryEntity(
                (PlaceOfDeliveryEntity) dbExportEntity(sentNumber,"placeofdelivery"));
        exportedData.setCarrierEntity(
                (CarrierEntity) dbExportEntity(sentNumber,"carrier"));
        exportedData.setGoodsInformationEntity(
                (GoodsInformationEntity) dbExportEntity(sentNumber,"goodsinformation"));
        exportedData.setMeanOfTransportEntity(
                (MeanOfTransportEntity) dbExportEntity(sentNumber,"meanoftransport"));
        return exportedData;
    }
    
    private static Object dbExportEntity(String sentNumber, String entity){
        Session session = sessionFactory.openSession();
        Object result = null;
        try {
            Query query = session.createQuery("FROM "+ entity + " WHERE sentnumber LIKE :sent");
            query.setParameter("sent", sentNumber);
            result = query.getSingleResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        catch( NoResultException e) {
        }
        finally{
            session.close();
            return result;
        }
    }
}
