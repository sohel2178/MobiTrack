package com.mobitrackbd.mobitrack.Model;

import java.io.Serializable;

/**
 * Created by IMATPC-12 on 07-Feb-18.
 */

public class User implements Serializable {
    private String user_id, user_type_id, customer_id, user_primary_account, user_login_email, user_name, primary_phone,
        cell_phone, insert_date_time, inserted_by, update_date_time, updated_by, user_status, customer_type_id, customer_name,
        customer_primary_email, customer_primary_phone, customer_secondary_phone, customer_address, customer_inserted_by,
        customer_insert_date_time, customer_updated_by, customer_update_date_time, customer_activated_by,
        customer_sms, customer_cost_per_device, customer_cost_per_vehicle,customer_status;

    public User(){

    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_type_id() {
        return user_type_id;
    }

    public void setUser_type_id(String user_type_id) {
        this.user_type_id = user_type_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getUser_primary_account() {
        return user_primary_account;
    }

    public void setUser_primary_account(String user_primary_account) {
        this.user_primary_account = user_primary_account;
    }

    public String getUser_login_email() {
        return user_login_email;
    }

    public void setUser_login_email(String user_login_email) {
        this.user_login_email = user_login_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPrimary_phone() {
        return primary_phone;
    }

    public void setPrimary_phone(String primary_phone) {
        this.primary_phone = primary_phone;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public String getInsert_date_time() {
        return insert_date_time;
    }

    public void setInsert_date_time(String insert_date_time) {
        this.insert_date_time = insert_date_time;
    }

    public String getInserted_by() {
        return inserted_by;
    }

    public void setInserted_by(String inserted_by) {
        this.inserted_by = inserted_by;
    }

    public String getUpdate_date_time() {
        return update_date_time;
    }

    public void setUpdate_date_time(String update_date_time) {
        this.update_date_time = update_date_time;
    }

    public String getUpdated_by() {
        return updated_by;
    }

    public void setUpdated_by(String updated_by) {
        this.updated_by = updated_by;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getCustomer_type_id() {
        return customer_type_id;
    }

    public void setCustomer_type_id(String customer_type_id) {
        this.customer_type_id = customer_type_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_primary_email() {
        return customer_primary_email;
    }

    public void setCustomer_primary_email(String customer_primary_email) {
        this.customer_primary_email = customer_primary_email;
    }

    public String getCustomer_primary_phone() {
        return customer_primary_phone;
    }

    public void setCustomer_primary_phone(String customer_primary_phone) {
        this.customer_primary_phone = customer_primary_phone;
    }

    public String getCustomer_secondary_phone() {
        return customer_secondary_phone;
    }

    public void setCustomer_secondary_phone(String customer_secondary_phone) {
        this.customer_secondary_phone = customer_secondary_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_inserted_by() {
        return customer_inserted_by;
    }

    public void setCustomer_inserted_by(String customer_inserted_by) {
        this.customer_inserted_by = customer_inserted_by;
    }

    public String getCustomer_insert_date_time() {
        return customer_insert_date_time;
    }

    public void setCustomer_insert_date_time(String customer_insert_date_time) {
        this.customer_insert_date_time = customer_insert_date_time;
    }

    public String getCustomer_updated_by() {
        return customer_updated_by;
    }

    public void setCustomer_updated_by(String customer_updated_by) {
        this.customer_updated_by = customer_updated_by;
    }

    public String getCustomer_update_date_time() {
        return customer_update_date_time;
    }

    public void setCustomer_update_date_time(String customer_update_date_time) {
        this.customer_update_date_time = customer_update_date_time;
    }

    public String getCustomer_activated_by() {
        return customer_activated_by;
    }

    public void setCustomer_activated_by(String customer_activated_by) {
        this.customer_activated_by = customer_activated_by;
    }

    public String getCustomer_sms() {
        return customer_sms;
    }

    public void setCustomer_sms(String customer_sms) {
        this.customer_sms = customer_sms;
    }

    public String getCustomer_cost_per_device() {
        return customer_cost_per_device;
    }

    public void setCustomer_cost_per_device(String customer_cost_per_device) {
        this.customer_cost_per_device = customer_cost_per_device;
    }

    public String getCustomer_cost_per_vehicle() {
        return customer_cost_per_vehicle;
    }

    public void setCustomer_cost_per_vehicle(String customer_cost_per_vehicle) {
        this.customer_cost_per_vehicle = customer_cost_per_vehicle;
    }

    public String getCustomer_status() {
        return customer_status;
    }

    public void setCustomer_status(String customer_status) {
        this.customer_status = customer_status;
    }


}
