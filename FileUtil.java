import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.util.*;


import com.ibm.wala.classLoader.IClass;
import com.ibm.wala.classLoader.IField;
import com.ibm.wala.classLoader.IMethod;
import com.ibm.wala.ipa.callgraph.CGNode;
import com.ibm.wala.ssa.IR;
import com.ibm.wala.ssa.SSAArrayLengthInstruction;
import com.ibm.wala.ssa.SSAArrayLoadInstruction;
import com.ibm.wala.ssa.SSAArrayStoreInstruction;
import com.ibm.wala.ssa.SSABinaryOpInstruction;
import com.ibm.wala.ssa.SSACheckCastInstruction;
import com.ibm.wala.ssa.SSAComparisonInstruction;
import com.ibm.wala.ssa.SSAConditionalBranchInstruction;
import com.ibm.wala.ssa.SSAConversionInstruction;
import com.ibm.wala.ssa.SSAGetCaughtExceptionInstruction;
import com.ibm.wala.ssa.SSAGetInstruction;
import com.ibm.wala.ssa.SSAGotoInstruction;
import com.ibm.wala.ssa.SSAInstanceofInstruction;
import com.ibm.wala.ssa.SSAInstruction;
import com.ibm.wala.ssa.SSAInvokeInstruction;
import com.ibm.wala.ssa.SSALoadMetadataInstruction;
import com.ibm.wala.ssa.SSAMonitorInstruction;
import com.ibm.wala.ssa.SSANewInstruction;
import com.ibm.wala.ssa.SSAPhiInstruction;
import com.ibm.wala.ssa.SSAPiInstruction;
import com.ibm.wala.ssa.SSAPutInstruction;
import com.ibm.wala.ssa.SSAReturnInstruction;
import com.ibm.wala.ssa.SSASwitchInstruction;
import com.ibm.wala.ssa.SSAThrowInstruction;
import com.ibm.wala.ssa.SSAUnaryOpInstruction;
import com.ibm.wala.types.FieldReference;
import com.ibm.wala.types.TypeReference;
import com.ibm.wala.types.MethodReference;
import com.ibm.wala.ipa.callgraph.cha.CHACallGraph;
import com.ibm.wala.classLoader.ShrikeClass;

public class FileUtil {

    public static boolean isOpenStatement(Graph<String> g, Map<String, Set<String>> nodeToValue, Set<String> startingNodes) {
        // System.out.println("In constraints not fulfilled");
        
    }

    public static boolean isCloseStatement(Graph<String> g, Map<String, Set<String>> nodeToValue, Set<String> startingNodes) {
        // System.out.println("In constraints not fulfilled");
        
    }

    public static boolean asdf(Graph<String> g, Map<String, Set<String>> nodeToValue) {
        Set<String> nodes = g.getNodes();
        for(String node : nodes) {
            Set<String> neighbors = g.getEdges(node);
            Set<String> nodeValues = nodeToValue.get(node);
            if(neighbors==null || nodeValues==null) {
                continue;
            }
            for(String neighbor : neighbors) {
                if(nodeToValue.get(neighbor) == null)
                    nodeToValue.put(neighbor, new HashSet<String>());
                nodeToValue.get(neighbor).addAll(nodeValues);
            }
        }
    }

    public static boolean constraintsNotPropagated(Graph<String> g, Map<String, Set<String>> nodeToValue, String node) {
        Set<String> nodeValues = nodeToValue.get(node);
        Set<String> neighbors = g.getEdges(node);
        if(neighbors != null && nodeValues != null) {
            for(String nodeVal : nodeValues) {
                for(String neighbor : neighbors) {
                    if(nodeToValue.get(neighbor) == null || !nodeToValue.get(neighbor).contains(nodeVal)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}