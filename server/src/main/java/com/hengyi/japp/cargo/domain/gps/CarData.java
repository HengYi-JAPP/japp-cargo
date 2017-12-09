package com.hengyi.japp.cargo.domain.gps;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by jzb on 16-3-28.
 */
public class CarData implements Serializable {
    private String carNo;

    public String getCarNo() {
        return carNo;
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarData carData = (CarData) o;
        return Objects.equals(carNo, carData.carNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carNo);
    }

    @Override
    public String toString() {
        return "CarData{" +
                "carNo='" + carNo + '\'' +
                '}';
    }
}
