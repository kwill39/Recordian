package chart;

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
