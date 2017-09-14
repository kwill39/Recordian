package chart;

/**
 * Used by charts to specify the query needed to gather their data
 *
 * @author  Kyle Williams
 * @since   Version 3
 */
public enum ChartDataQuery {
    COMPANY("SELECT * FROM logEntries WHERE logEntryCompanyName = ?"),
    LOCATION("SELECT * FROM logEntries WHERE logEntryLocationName = ?"),
    SUPERVISOR("SELECT * FROM logEntries WHERE logEntrySupervisorDisplayName = ?");

    private String sqlQuery;

    ChartDataQuery(String sqlQuery) {
        this.sqlQuery = sqlQuery;
    }

    public String getSqlQuery() {
        return sqlQuery;
    }
}
