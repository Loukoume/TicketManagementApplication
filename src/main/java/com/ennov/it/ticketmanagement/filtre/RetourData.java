package com.yaayi.gschool.filtre;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class RetourData<T> {
    private String status;
     private String description;
    private T data;

    public RetourData(String s, String description) {
        this.status=s;
        this.description=description;
    }

    public RetourData(String status, String description, T data) {
        this.status = status;
        this.description = description;
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "RetourData{" +
                "status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", data=" + data +
                '}';
    }
}
