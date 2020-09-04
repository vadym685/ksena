package com.company.ksena.entity.api.gps_gt_api;

import java.io.Serializable;

public class TrackMessage implements Serializable {
    public long c = 0L;
    public double dist = 0.0;
    //Координаты
    public double lat = 0.0;
    public double lon = 0.0;
    public long z = 0L;

    public long s = 0L;
    public long sc = 0L;
    public long signal = 0L;
    public long speed = 0L;
    // Время данного сообщения
    public long t = 0L;
    // Типа продолжительность
    public long time = 0L;

    // Total volume Отгруженно/Слито литры - Для цистерны
    // Штатный пробег - Для транспорта
    public double can_full_mileage = 0.0;

    // Отгруженно/Слито кг - Для цистерны
    // Датчик обсолютного расхода топлива - Для транспорта
    public double can_full_fuel_cons = 0.0;

    // Плотность газа - Для цистерны
    // Уровень топлива - Для транспорта
    public double can_fuel_level = 0.0;
    // Пробег
    public double gps_full_mileage = 0.0;

    public double can_axle_load1 = 0.0;
    public double can_axle_load2 = 0.0;

    // Delivery Доставка литры
    public double can_axle_load3 = 0.0;
    // Delivery Доставка кг
    public double can_axle_load4 = 0.0;
    // Частный режим  вкл.\выкл.
    public double can_axle_load5 = 0.0;

    // Доставка - номер отгрузки - Для цистерны
    // Моточасов - Для транспорта
    public double can_eng_full_time = 0.0;

    // Температура (для газовоза)- нужно делить на 100 = пример 1389 = 13.89  - Для цистерны
    // Обротов в сек - Для транспорта
    public double can_rpm = 0.0;
    public double can_sequrity_state = 0.0;
    public double cell_id = 0.0;


    public double gsm = 0.0;
    public double gsm_st = 0.0;
    public double hdop = 0.0;
    public double i = 0.0;
    public double info_messages = 0.0;
    public double lac = 0.0;

    public double lc = 0.0;
    public double lls1_temp = 0.0;
    public double lls1_val = 0.0;

    public double mcc = 0.0;
    public double mnc = 0.0;

    // Движение 0 нет, 1 да - наверное
    public double mw = 0.0;
    public double nav_st = 0.0;
    public double o = 0.0;

    // Двигатель вкл.\выкл. или питание системы в В- Для цистерны
    // Датчик напряжения - Для транспорта
    public double pwr_ext = 0.0;
    public double pwr_in = 0.0;
    public double pwr_int = 0.0;
    public double rt = 0.0;

    public double sim_in = 0.0;
    public double sim_t = 0.0;

    public double st0 = 0.0;
    public double st1 = 0.0;
    public double st2 = 0.0;

    public long getC() {
        return c;
    }

    public void setC(long c) {
        this.c = c;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getZ() {
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }

    public long getS() {
        return s;
    }

    public void setS(long s) {
        this.s = s;
    }

    public long getSc() {
        return sc;
    }

    public void setSc(long sc) {
        this.sc = sc;
    }

    public long getSignal() {
        return signal;
    }

    public void setSignal(long signal) {
        this.signal = signal;
    }

    public long getSpeed() {
        return speed;
    }

    public void setSpeed(long speed) {
        this.speed = speed;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getCan_full_mileage() {
        return can_full_mileage;
    }

    public void setCan_full_mileage(double can_full_mileage) {
        this.can_full_mileage = can_full_mileage;
    }

    public double getCan_full_fuel_cons() {
        return can_full_fuel_cons;
    }

    public void setCan_full_fuel_cons(double can_full_fuel_cons) {
        this.can_full_fuel_cons = can_full_fuel_cons;
    }

    public double getCan_fuel_level() {
        return can_fuel_level;
    }

    public void setCan_fuel_level(double can_fuel_level) {
        this.can_fuel_level = can_fuel_level;
    }

    public double getGps_full_mileage() {
        return gps_full_mileage;
    }

    public void setGps_full_mileage(double gps_full_mileage) {
        this.gps_full_mileage = gps_full_mileage;
    }

    public double getCan_axle_load1() {
        return can_axle_load1;
    }

    public void setCan_axle_load1(double can_axle_load1) {
        this.can_axle_load1 = can_axle_load1;
    }

    public double getCan_axle_load2() {
        return can_axle_load2;
    }

    public void setCan_axle_load2(double can_axle_load2) {
        this.can_axle_load2 = can_axle_load2;
    }

    public double getCan_axle_load3() {
        return can_axle_load3;
    }

    public void setCan_axle_load3(double can_axle_load3) {
        this.can_axle_load3 = can_axle_load3;
    }

    public double getCan_axle_load4() {
        return can_axle_load4;
    }

    public void setCan_axle_load4(double can_axle_load4) {
        this.can_axle_load4 = can_axle_load4;
    }

    public double getCan_axle_load5() {
        return can_axle_load5;
    }

    public void setCan_axle_load5(double can_axle_load5) {
        this.can_axle_load5 = can_axle_load5;
    }

    public double getCan_eng_full_time() {
        return can_eng_full_time;
    }

    public void setCan_eng_full_time(double can_eng_full_time) {
        this.can_eng_full_time = can_eng_full_time;
    }

    public double getCan_rpm() {
        return can_rpm;
    }

    public void setCan_rpm(double can_rpm) {
        this.can_rpm = can_rpm;
    }

    public double getCan_sequrity_state() {
        return can_sequrity_state;
    }

    public void setCan_sequrity_state(double can_sequrity_state) {
        this.can_sequrity_state = can_sequrity_state;
    }

    public double getCell_id() {
        return cell_id;
    }

    public void setCell_id(double cell_id) {
        this.cell_id = cell_id;
    }

    public double getGsm() {
        return gsm;
    }

    public void setGsm(double gsm) {
        this.gsm = gsm;
    }

    public double getGsm_st() {
        return gsm_st;
    }

    public void setGsm_st(double gsm_st) {
        this.gsm_st = gsm_st;
    }

    public double getHdop() {
        return hdop;
    }

    public void setHdop(double hdop) {
        this.hdop = hdop;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getInfo_messages() {
        return info_messages;
    }

    public void setInfo_messages(double info_messages) {
        this.info_messages = info_messages;
    }

    public double getLac() {
        return lac;
    }

    public void setLac(double lac) {
        this.lac = lac;
    }

    public double getLc() {
        return lc;
    }

    public void setLc(double lc) {
        this.lc = lc;
    }

    public double getLls1_temp() {
        return lls1_temp;
    }

    public void setLls1_temp(double lls1_temp) {
        this.lls1_temp = lls1_temp;
    }

    public double getLls1_val() {
        return lls1_val;
    }

    public void setLls1_val(double lls1_val) {
        this.lls1_val = lls1_val;
    }

    public double getMcc() {
        return mcc;
    }

    public void setMcc(double mcc) {
        this.mcc = mcc;
    }

    public double getMnc() {
        return mnc;
    }

    public void setMnc(double mnc) {
        this.mnc = mnc;
    }

    public double getMw() {
        return mw;
    }

    public void setMw(double mw) {
        this.mw = mw;
    }

    public double getNav_st() {
        return nav_st;
    }

    public void setNav_st(double nav_st) {
        this.nav_st = nav_st;
    }

    public double getO() {
        return o;
    }

    public void setO(double o) {
        this.o = o;
    }

    public double getPwr_ext() {
        return pwr_ext;
    }

    public void setPwr_ext(double pwr_ext) {
        this.pwr_ext = pwr_ext;
    }

    public double getPwr_in() {
        return pwr_in;
    }

    public void setPwr_in(double pwr_in) {
        this.pwr_in = pwr_in;
    }

    public double getPwr_int() {
        return pwr_int;
    }

    public void setPwr_int(double pwr_int) {
        this.pwr_int = pwr_int;
    }

    public double getRt() {
        return rt;
    }

    public void setRt(double rt) {
        this.rt = rt;
    }

    public double getSim_in() {
        return sim_in;
    }

    public void setSim_in(double sim_in) {
        this.sim_in = sim_in;
    }

    public double getSim_t() {
        return sim_t;
    }

    public void setSim_t(double sim_t) {
        this.sim_t = sim_t;
    }

    public double getSt0() {
        return st0;
    }

    public void setSt0(double st0) {
        this.st0 = st0;
    }

    public double getSt1() {
        return st1;
    }

    public void setSt1(double st1) {
        this.st1 = st1;
    }

    public double getSt2() {
        return st2;
    }

    public void setSt2(double st2) {
        this.st2 = st2;
    }
}