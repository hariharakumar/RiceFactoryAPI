package com.projects.ricefactory.utils;

/**
 * Created by hearlapati on 8/1/17.
 */
public class MySqlQueries {

    public final static String SQL_USER_GET_BY_ID =
            "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2," +
            "a.address_1, a.address_2,a.city,a.state,a.zip_code," +
            "u.password,ua.address_id " +
            "from user u, address a, userAddress ua " +
            "where ua.user_id=? " +
            "and ua.address_id = a.id " +
            "and ua.user_id=u.id";

    public final static String SQL_USER_GET_BY_EMAIL =
            "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2," +
            "a.address_1, a.address_2,a.city,a.state,a.zip_code," +
            "u.password,ua.address_id " +
            "from user u, address a,userAddress ua " +
            "where u.email=? " +
            "and ua.address_id = a.id " +
            "and ua.user_id=u.id ";

    public final static String SQL_USER_CREATE =
            "insert into user(first_name,last_name,email,phone_number_1, phone_number_2, password, enabled)" +
            " values(?,?,?,?,?,?,?)";
    public final static String SQL_USER_UPDATE =
            "update user set first_name=?,last_name=?,email=?,phone_number_1=?,phone_number_2=?,password=?,enabled=? where id=?";

    public final static String SQL_USER_TO_ADDRESS_CREATE = "insert into userAddress(user_id, address_id) values(?,?)";

    public final static String SQL_USER_TO_ADDRESS_UPDATE = "update userAddress set user_id=?, address_id=? where id=?";

    public final static String SQL_USER_TO_ADDRESS_DELETE = "delete from userAddress where user_id=? and address_id=?";

    public final static String SQL_USER_TO_ADDRESS_GET = "";

    public final static String SQL_GET_ALL_USERS =
            "select u.id, u.first_name, u.last_name, u.email, u.phone_number_1, u.phone_number_2," +
            "a.address_1, a.address_2,a.city,a.state,a.zip_code," +
            "u.password,ua.address_id " +
            "from user u, address a,userAddress ua " +
            "where ua.address_id = a.id and ua.user_id=u.id";

    public final static String SQL_GET_ID_BY_EMAIL = "select id from user where email=?";

    public final static String SQL_ADDRESS_CREATE = "insert into address(address_1,address_2,city,state,zip_code) values(?,?,?,?,?)";

    public final static String SQL_ADDRESS_UPDATE = "update address set address_1=?,address_2=?,city=?,state=?,zip_code=? where id=?";

    public final static String SQL_RICE_TYPE_GET_INTERNAL_NAME =
            "select rt.id, rt.display_name,rt.internal_name,rt.price_per_kg,rt.description" +
            " from riceType rt " +
            "where internal_name=?";
    public final static String SQL_RICE_TYPE_GET_DISPLAY_NAME =
            "select rt.id, rt.display_name,rt.internal_name,rt.price_per_kg,rt.description" +
                    " from riceType rt " +
                    "where display_name=?";
    public final static String SQL_RICE_TYPE_GET_BY_ID =
            "select rt.id, rt.display_name,rt.internal_name,rt.price_per_kg,rt.description " +
            "from riceType rt " +
            "where rt.id =?";

    public final static String SQL_RICE_TYPE_ALL_GET =
            "select rt.id, rt.display_name,rt.internal_name,rt.price_per_kg,rt.description" +
                    " from riceType rt ";

    public final static String SQL_ORDER_CREATE =
            "insert into order(polished,riceTypeId,amount_in_kgs,total_cost,delivery_date,user_to_address_id,customer_notes,cancelled)" +
            " values(?,?,?,?,?,?,?,?)";

    public final static String SQL_ORDER_UPDATE =
            "update order set polished=?,riceTypeId=?,amount_in_kgs=?,total_cost=?,delivery_date=?,user_to_address_id=?,customer_notes=?,cancelled=?";

    public final static String SQL_ORDER_GET =
            "select o.polished, o.riceTypeId, o.amount_in_kgs,o.total_cost,o.delivery_date,o.user_to_address_id,o.date_created,o.last_updated,o.customer_notes, o.cancelled " +
            "from order o where o.id=?";

    public final static String SQL_GET_ORDERS_BY_USER_ID =
            "select rt.`display_name`, o.`amount_in_kgs`, o.`total_cost`, o.`delivery_date`, o.`customer_notes`, o.`polished`," +
            "o.`user_to_address_id`, o.`date_created`, o.`last_updated`, o.`cancelled` " +
            "from `order` o, `userAddress` ua, riceType rt " +
            "where o.`user_to_address_id` = ua.`id` and o.`riceTypeId` = rt.`id` and ua.`user_id` = ?";


}