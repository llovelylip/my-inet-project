/*
 * Created on Nov 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package inet.db.javagame;
import java.math.*;
/**
 * @author Huyen
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JavaGameCat {
	private BigDecimal id;
    private String name;
    private BigDecimal parentId;
    private BigDecimal groupId;
    private int level;    
    private int status;
    private int priority;
    
    public JavaGameCat() {
    }
    public BigDecimal getId() {
        return id;
    }
    public void setId(BigDecimal value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }
    public void setName(String value) {
        this.name = value;
    }
    public BigDecimal getParentId() {
        return parentId;
    }
    public void setParentId(BigDecimal value) {
        this.parentId = value;
    }

    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setGroupId(BigDecimal groupId){
    	this.groupId=groupId;
    }
    public BigDecimal getGroupId(){
    	return this.groupId;
    }
    public void  setLevel(int level){
    	this.level=level;
    }
    public int getLevel(){
    	return level;
    }
   
}
