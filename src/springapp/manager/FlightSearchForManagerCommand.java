package springapp.manager;

/**
 * A Spring Framework Command class for FlightSearchForManager
 */
public class FlightSearchForManagerCommand {

   private Integer flightNo;
   private String airline;

   private String departLocation;
   private String arrivalLocation;

   private Boolean optDepartDate;
   private Integer departYear;
   private Integer departMonth;
   private Integer departDay;
   private Integer departHour;

   private Boolean optArriveDate;
   private Integer arriveYear;
   private Integer arriveMonth;
   private Integer arriveDay;
   private Integer arriveHour;

   /**
    * @param flightNo the flightNo to set
    */
   public void setFlightNo(Integer flightNo) {
      this.flightNo = flightNo;
   }

   /**
    * @return the flightNo
    */
   public Integer getFlightNo() {
      return flightNo;
   }

   /**
    * @param airline the airline to set
    */
   public void setAirline(String airline) {
      this.airline = airline;
   }

   /**
    * @return the airline
    */
   public String getAirline() {
      return airline;
   }

   /**
    * @param departLocation the departLocation to set
    */
   public void setDepartLocation(String departLocation) {
      this.departLocation = departLocation;
   }

   /**
    * @return the departLocation
    */
   public String getDepartLocation() {
      return departLocation;
   }

   /**
    * @param arrivalLocation the arrivalLocation to set
    */
   public void setArrivalLocation(String arrivalLocation) {
      this.arrivalLocation = arrivalLocation;
   }

   /**
    * @return the arrivalLocation
    */
   public String getArrivalLocation() {
      return arrivalLocation;
   }

   /**
    * @param departYear the departYear to set
    */
   public void setDepartYear(Integer departYear) {
      this.departYear = departYear;
   }

   /**
    * @return the departYear
    */
   public Integer getDepartYear() {
      return departYear;
   }

   /**
    * @param departMonth the departMonth to set
    */
   public void setDepartMonth(Integer departMonth) {
      this.departMonth = departMonth;
   }

   /**
    * @return the departMonth
    */
   public Integer getDepartMonth() {
      return departMonth;
   }

   /**
    * @param departDay the departDay to set
    */
   public void setDepartDay(Integer departDay) {
      this.departDay = departDay;
   }

   /**
    * @return the departDay
    */
   public Integer getDepartDay() {
      return departDay;
   }

   /**
    * @param departHour the departHour to set
    */
   public void setDepartHour(Integer departHour) {
      this.departHour = departHour;
   }

   /**
    * @return the departHour
    */
   public Integer getDepartHour() {
      return departHour;
   }

   /**
    * @param arriveYear the arriveYear to set
    */
   public void setArriveYear(Integer arriveYear) {
      this.arriveYear = arriveYear;
   }

   /**
    * @return the arriveYear
    */
   public Integer getArriveYear() {
      return arriveYear;
   }

   /**
    * @param arriveMonth the arriveMonth to set
    */
   public void setArriveMonth(Integer arriveMonth) {
      this.arriveMonth = arriveMonth;
   }

   /**
    * @return the arriveMonth
    */
   public Integer getArriveMonth() {
      return arriveMonth;
   }

   /**
    * @param arriveDay the arriveDay to set
    */
   public void setArriveDay(Integer arriveDay) {
      this.arriveDay = arriveDay;
   }

   /**
    * @return the arriveDay
    */
   public Integer getArriveDay() {
      return arriveDay;
   }

   /**
    * @param arriveHour the arriveHour to set
    */
   public void setArriveHour(Integer arriveHour) {
      this.arriveHour = arriveHour;
   }

   /**
    * @return the arriveHour
    */
   public Integer getArriveHour() {
      return arriveHour;
   }

   /**
    * @param optArriveDate the optArriveDate to set
    */
   public void setOptArriveDate(Boolean optArriveDate) {
      this.optArriveDate = optArriveDate;
   }

   /**
    * @return the optArriveDate
    */
   public Boolean getOptArriveDate() {
      return optArriveDate;
   }

   /**
    * @param optDepartDate the optDepartDate to set
    */
   public void setOptDepartDate(Boolean optDepartDate) {
      this.optDepartDate = optDepartDate;
   }

   /**
    * @return the optDepartDate
    */
   public Boolean getOptDepartDate() {
      return optDepartDate;
   }
}
