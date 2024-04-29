package calendar;

import java.time.ZonedDateTime;

public class CalendarActivity {
    private ZonedDateTime serviceDate;
    private String serviceName;
    private Integer serviceId;

    public CalendarActivity(ZonedDateTime serviceDate, String serviceName, Integer serviceId) {
        this.serviceDate = serviceDate;
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }

    public ZonedDateTime getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(ZonedDateTime serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "CalendarActivity{" +
                "serviceDate=" + serviceDate +
                ", serviceName='" + serviceName + '\'' +
                ", serviceId=" + serviceId +
                '}';
    }
}
