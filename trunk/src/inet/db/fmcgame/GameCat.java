package inet.db.fmcgame;

import java.math.BigDecimal;

public class GameCat {
	private BigDecimal id;
    private String name;
    private int indentLevel;
    private BigDecimal parentId;
    private int groupId;
    private int status;

    public BigDecimal getId() {
        return id;
    }
    public void setId(BigDecimal id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getParentId() {
        return parentId;
    }
    public void setParentId(BigDecimal parentId) {
        this.parentId = parentId;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getIndentLevel() {
        return indentLevel;
    }
    public void setIndentLevel(int indentLevel) {
        this.indentLevel = indentLevel;
    }
    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
