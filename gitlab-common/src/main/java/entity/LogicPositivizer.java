package entity;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import org.apache.commons.lang.StringUtils;

import java.util.*;

public class LogicPositivizer {

    public static Map<String, Object> parser(String sourceCode) {
        CompilationUnit cu = null;
        try {
            cu = StaticJavaParser.parse(sourceCode);
        } catch (Exception e) {
            System.out.println(sourceCode);
            return null;
        }
        List<ClassOrInterfaceDeclaration> classes = cu.findAll(ClassOrInterfaceDeclaration.class);
        Map<String, Object> resMap = new HashMap<>();
        List<Method> methodList = new ArrayList<>();

        for (ClassOrInterfaceDeclaration aClass : classes) {
            if (aClass.isInterface()) continue;
            for (MethodDeclaration method : aClass.getMethods()) {
                if (method.getNameAsString().equals("main")) {
                    resMap.put("mainClass", aClass.getNameAsString());
                }
                methodList.add(new Method(aClass.getNameAsString(),
                        method.getNameAsString(),
                        method.getBegin().map(begin -> begin.line).orElse(null),
                        method.getEnd().map(end -> end.line).orElse(null),
                        method.toString()));
            }
        }

        resMap.put("methods", methodList);
        return resMap;
    }

    public static String getMethodBody(Method method, String sourceCode) {
        String[] codeLines = sourceCode.split("\\n");
        List<String> code = new ArrayList<>(Arrays.asList(codeLines)
                .subList(method.getBeginLine() - 1, method.getEndLine()));
        return StringUtils.join(code, "\n");
    }

}
