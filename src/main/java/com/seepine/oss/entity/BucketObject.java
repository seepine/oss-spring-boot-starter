package com.seepine.oss.entity;

import com.qcloud.cos.model.Owner;
import lombok.Data;

import java.util.Date;

/**
 * @author Seepine
 */
@Data
public class BucketObject {
    private String name;
    private Owner owner;
    private Date creationDate;
    private String location;
}
