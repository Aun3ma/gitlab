package entity;

public class Method {
    private String relevantClass;
    private String methodName;
    private Integer beginLine;
    private Integer endLine;
    private String body;

    public Method(String relevantClass, String methodName, Integer beginLine, Integer endLine, String body) {
        this.relevantClass = relevantClass;
        this.methodName = methodName;
        this.beginLine = beginLine;
        this.endLine = endLine;
        this.body = body;
    }

    public String getRelevantClass() {
        return relevantClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public Integer getBeginLine() {
        return beginLine;
    }

    public Integer getEndLine() {
        return endLine;
    }

    public String getBody() {
        return body;
    }
}