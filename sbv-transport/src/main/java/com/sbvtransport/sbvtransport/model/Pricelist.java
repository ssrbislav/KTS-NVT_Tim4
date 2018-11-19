package com.sbvtransport.sbvtransport.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Range;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pricelist")
public class Pricelist {

  // TODO #1: Finish this class.

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="id", unique=true, nullable=false)
  private Long id;

  @Column(name="valid_since", unique=false, nullable=false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
  private Date valid_since;
  @Column(name="valid_until", unique=false, nullable=false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy.")
  private Date valid_until;

  @Column(name = "active", unique = false, nullable = false)
  private Boolean active;

  @Column(name = "senior_discount_percentage", unique = false, nullable = false)
  @Range(min = 0, max = 100)
  private Integer senior_discount_percentage;
  @Column(name = "student_discount_percentage", unique = false, nullable = false)
  @Range(min = 0, max = 100)
  private Integer student_discount_percentage;
  @Column(name = "standard_discount_percentage", unique = false, nullable = false)
  @Range(min = 0, max = 100)
  private Integer standard_discount_percentage;

  @Column(name = "double_zone_premium_percentage", unique = false, nullable = false)
  @Range(min = 100)
  private Integer double_zone_premium_percentage;


  @Column(name = "bus_one_use_price", unique = false, nullable = false)
  private Double bus_one_use_price;
  @Column(name = "bus_daily_use_price", unique = false, nullable = false)
  private Double bus_daily_use_price;
  @Column(name = "bus_monthly_use_price", unique = false, nullable = false)
  private Double bus_monthly_use_price;
  @Column(name = "bus_yearly_use_price", unique = false, nullable = false)
  private Double bus_yearly_use_price;

  @Column(name = "subway_one_use_price", unique = false, nullable = false)
  private Double subway_one_use_price;
  @Column(name = "subway_daily_use_price", unique = false, nullable = false)
  private Double subway_daily_use_price;
  @Column(name = "subway_monthly_use_price", unique = false, nullable = false)
  private Double subway_monthly_use_price;
  @Column(name = "subway_yearly_use_price", unique = false, nullable = false)
  private Double subway_yearly_use_price;

  @Column(name = "trolley_one_use_price", unique = false, nullable = false)
  private Double trolley_one_use_price;
  @Column(name = "trolley_daily_use_price", unique = false, nullable = false)
  private Double trolley_daily_use_price;
  @Column(name = "trolley_monthly_use_price", unique = false, nullable = false)
  private Double trolley_monthly_use_price;
  @Column(name = "trolley_yearly_use_price", unique = false, nullable = false)
  private Double trolley_yearly_use_price;

  public Pricelist(Date valid_since, Date valid_until, Boolean active, @Range(min = 0, max = 100) Integer senior_discount_percentage, @Range(min = 0, max = 100) Integer student_discount_percentage, @Range(min = 0, max = 100) Integer standard_discount_percentage, @Range(min = 100) Integer double_zone_premium_percentage, Double bus_one_use_price, Double bus_daily_use_price, Double bus_monthly_use_price, Double bus_yearly_use_price, Double subway_one_use_price, Double subway_daily_use_price, Double subway_monthly_use_price, Double subway_yearly_use_price, Double trolley_one_use_price, Double trolley_daily_use_price, Double trolley_monthly_use_price, Double trolley_yearly_use_price) {
    this.valid_since = valid_since;
    this.valid_until = valid_until;
    this.active = active;
    this.senior_discount_percentage = senior_discount_percentage;
    this.student_discount_percentage = student_discount_percentage;
    this.standard_discount_percentage = standard_discount_percentage;
    this.double_zone_premium_percentage = double_zone_premium_percentage;
    this.bus_one_use_price = bus_one_use_price;
    this.bus_daily_use_price = bus_daily_use_price;
    this.bus_monthly_use_price = bus_monthly_use_price;
    this.bus_yearly_use_price = bus_yearly_use_price;
    this.subway_one_use_price = subway_one_use_price;
    this.subway_daily_use_price = subway_daily_use_price;
    this.subway_monthly_use_price = subway_monthly_use_price;
    this.subway_yearly_use_price = subway_yearly_use_price;
    this.trolley_one_use_price = trolley_one_use_price;
    this.trolley_daily_use_price = trolley_daily_use_price;
    this.trolley_monthly_use_price = trolley_monthly_use_price;
    this.trolley_yearly_use_price = trolley_yearly_use_price;
  }

  public Pricelist() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getValid_since() {
    return valid_since;
  }

  public void setValid_since(Date valid_since) {
    this.valid_since = valid_since;
  }

  public Date getValid_until() {
    return valid_until;
  }

  public void setValid_until(Date valid_until) {
    this.valid_until = valid_until;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Integer getSenior_discount_percentage() {
    return senior_discount_percentage;
  }

  public void setSenior_discount_percentage(Integer senior_discount_percentage) {
    this.senior_discount_percentage = senior_discount_percentage;
  }

  public Integer getStudent_discount_percentage() {
    return student_discount_percentage;
  }

  public void setStudent_discount_percentage(Integer student_discount_percentage) {
    this.student_discount_percentage = student_discount_percentage;
  }

  public Integer getStandard_discount_percentage() {
    return standard_discount_percentage;
  }

  public void setStandard_discount_percentage(Integer standard_discount_percentage) {
    this.standard_discount_percentage = standard_discount_percentage;
  }

  public Integer getDouble_zone_premium_percentage() {
    return double_zone_premium_percentage;
  }

  public void setDouble_zone_premium_percentage(Integer double_zone_premium_percentage) {
    this.double_zone_premium_percentage = double_zone_premium_percentage;
  }

  public Double getBus_one_use_price() {
    return bus_one_use_price;
  }

  public void setBus_one_use_price(Double bus_one_use_price) {
    this.bus_one_use_price = bus_one_use_price;
  }

  public Double getBus_daily_use_price() {
    return bus_daily_use_price;
  }

  public void setBus_daily_use_price(Double bus_daily_use_price) {
    this.bus_daily_use_price = bus_daily_use_price;
  }

  public Double getBus_monthly_use_price() {
    return bus_monthly_use_price;
  }

  public void setBus_monthly_use_price(Double bus_monthly_use_price) {
    this.bus_monthly_use_price = bus_monthly_use_price;
  }

  public Double getBus_yearly_use_price() {
    return bus_yearly_use_price;
  }

  public void setBus_yearly_use_price(Double bus_yearly_use_price) {
    this.bus_yearly_use_price = bus_yearly_use_price;
  }

  public Double getSubway_one_use_price() {
    return subway_one_use_price;
  }

  public void setSubway_one_use_price(Double subway_one_use_price) {
    this.subway_one_use_price = subway_one_use_price;
  }

  public Double getSubway_daily_use_price() {
    return subway_daily_use_price;
  }

  public void setSubway_daily_use_price(Double subway_daily_use_price) {
    this.subway_daily_use_price = subway_daily_use_price;
  }

  public Double getSubway_monthly_use_price() {
    return subway_monthly_use_price;
  }

  public void setSubway_monthly_use_price(Double subway_monthly_use_price) {
    this.subway_monthly_use_price = subway_monthly_use_price;
  }

  public Double getSubway_yearly_use_price() {
    return subway_yearly_use_price;
  }

  public void setSubway_yearly_use_price(Double subway_yearly_use_price) {
    this.subway_yearly_use_price = subway_yearly_use_price;
  }

  public Double getTrolley_one_use_price() {
    return trolley_one_use_price;
  }

  public void setTrolley_one_use_price(Double trolley_one_use_price) {
    this.trolley_one_use_price = trolley_one_use_price;
  }

  public Double getTrolley_daily_use_price() {
    return trolley_daily_use_price;
  }

  public void setTrolley_daily_use_price(Double trolley_daily_use_price) {
    this.trolley_daily_use_price = trolley_daily_use_price;
  }

  public Double getTrolley_monthly_use_price() {
    return trolley_monthly_use_price;
  }

  public void setTrolley_monthly_use_price(Double trolley_monthly_use_price) {
    this.trolley_monthly_use_price = trolley_monthly_use_price;
  }

  public Double getTrolley_yearly_use_price() {
    return trolley_yearly_use_price;
  }

  public void setTrolley_yearly_use_price(Double trolley_yearly_use_price) {
    this.trolley_yearly_use_price = trolley_yearly_use_price;
  }
}
