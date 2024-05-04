package calendar;

/**
 *
 * @author HP
 */
import java.time.LocalDateTime;
import java.util.Date;

public class CalendarActivity {
    private String title;
    private LocalDateTime dateStart;
    private Date dateEnd;
    private String description;

    public CalendarActivity() {
        // Default constructor
    }

    public CalendarActivity(String title, LocalDateTime dateStart, Date dateEnd, String description) {
        this.title = title;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CalendarActivity{" + "title=" + title + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", description=" + description + '}';
    }

    public void setClientName(String localisation) {
    }

    public double getClientName() {
        return 0;
    }
}