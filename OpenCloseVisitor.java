
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Iterator;
import java.util.*;

public class OpenCloseVisitor implements SSAInstruction.IVisitor {

	Map<String, String> openSet;
    Map<String, String> closeSet;
	String prefix;
	CHACallGraph cg;
	CGNode currNode;
	Map<String, String> variableToFile;
	Graph<String> typeGraph;
	public OpenCloseVisitor(Map<String, String> openSet, Map<String, String> closeSet, Map<String, String> variableToFile, Graph<String> typeGraph, String prefix, CHACallGraph cg, CGNode currNode) {
		this.openSet = openSet;
        this.closeSet = closeSet;
		this.variableToFile = variableToFile;
		this.typeGraph = typeGraph;
		this.prefix = prefix;
		this.cg = cg;
		this.currNode = currNode;
	}

	/* VISIT FUNCTIONS */
	@Override
	public void visitArrayLength(SSAArrayLengthInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitArrayLoad(SSAArrayLoadInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitArrayStore(SSAArrayStoreInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitBinaryOp(SSABinaryOpInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitCheckCast(SSACheckCastInstruction ins) {
		
	}

	@Override
	public void visitComparison(SSAComparisonInstruction ins) { 
		System.out.println(ins);
	}

	@Override
	public void visitConditionalBranch(SSAConditionalBranchInstruction ins) { 
		System.out.println(ins);
	}

	@Override
	public void visitConversion(SSAConversionInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitGet(SSAGetInstruction ins) {
	}

	@Override
	public void visitGetCaughtException(SSAGetCaughtExceptionInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitGoto(SSAGotoInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitInstanceof(SSAInstanceofInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitInvoke(SSAInvokeInstruction ins) {
        
		// System.out.println("Invoke---");
		// System.out.println(ins.getDeclaredTarget());
		// System.out.println(ins.getProgramCounter());
		// System.out.println(ins.iIndex());

		MethodReference met = ins.getDeclaredTarget();
		TypeReference cla = met.getDeclaringClass();

		System.out.println(cla.getName().toString());
		System.out.println(met.getName().toString());
		
		if(cla.getClassLoader().getName().toString().contains("Application")) {
			//Check for nio readAllLines, Desktop open

			//Desktop open-- it seems like you are never able to manually close (external process)
			// if(cla.getName().toString().contains("Ljava/awt/Desktop") && 
			// 	met.getName().toString().contains("open")) { //Desktop open-- need to see which file object
			// 	System.out.println("Opens Desktop open");
			// }

			//Seems to open/close in the same statement
			// if(cla.getName().toString().contains("Ljava/nio/file/Files") && 
			// 	met.getName().toString().contains("readAllLines")) { //nio readAllLines-- need to see which file object
			// 	System.out.println("Opens nio.readAllLines");
			// }


			//check for <init> FileInputStream, BufferedReader/Writer, FileReader/Writer, Scanner
			//if <init> File or <init> FileInputStream, make it a "File" object named after file string it opens
			if(met.getName().toString().contains("<init>")) {
				
				if(cla.getName().toString().contains("Ljava/io/FileInputStream")) { //FileInputStream new
					if(met.getParameterType(0).getName().toString().contains("Ljava/lang/String")) {
						variableToFile.put(prefix + "." + ins.getUse(0), prefix + "." + ins.getUse(1)); //Initialize as File object
					} else {
						//Get the file object it refers to
						//Either the variable itself or the object it could be is a file object
						Set<String> possibleObjs = typeGraph.getEdges(prefix + "." + ins.getUse(1));
						Iterator<String> iter = possibleObjs.iterator();
						while(iter.hasNext()) {
							String temp = iter.next();
							// System.out.println(temp);
							if(variableToFile.get(temp) != null) {
								//This file is open
								// System.out.println("FileInputStream open");
								// System.out.println(variableToFile.get(temp));
								// System.out.println(prefix + "." + ins.iIndex());
								openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(temp));
								variableToFile.put(prefix + "." + ins.getUse(0), variableToFile.get(temp));
							}
						}
						if(variableToFile.get(prefix + "." + ins.getUse(1)) != null) {
							//This file is open
							// System.out.println("FileInputStream open");
							// System.out.println(variableToFile.get(prefix + "." + ins.getUse(1)));
							// System.out.println(prefix + "." + ins.iIndex());
							openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(prefix + "." + ins.getUse(1)));
							variableToFile.put(prefix + "." + ins.getUse(0), variableToFile.get(prefix + "." + ins.getUse(1)));
						}
					}

				}
				if(cla.getName().toString().contains("Ljava/io/File")) { //File
					if(met.getParameterType(0).getName().toString().contains("Ljava/lang/String")) { 
						variableToFile.put(prefix + "." + ins.getUse(0), prefix + "." + ins.getUse(1)); //initialize as File Object
						//Identify File by the variable of its filepath string
					}
				}
				if(cla.getName().toString().contains("Ljava/io/BufferedReader")) { //BufferedReader new
					//Get the file object it refers to
					//Either the variable itself or the object it could be is a file object
					Set<String> possibleObjs = typeGraph.getEdges(prefix + "." + ins.getUse(1));
					Iterator<String> iter = possibleObjs.iterator();
					while(iter.hasNext()) {
						String temp = iter.next();
						if(variableToFile.get(temp) != null) {
							//This file is open-- mark it so for this instruction
							openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(temp));
						}
					}
					if(variableToFile.get(prefix + "." + ins.getUse(1)) != null) {
						//This file is open
						openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(prefix + "." + ins.getUse(1)));
					}
				}
				if(cla.getName().toString().contains("Ljava/io/FileReader")) { //FileReader new
					//Get the file object it refers to
					//Either the variable itself or the object it could be is a file object
					Set<String> possibleObjs = typeGraph.getEdges(prefix + "." + ins.getUse(1));
					Iterator<String> iter = possibleObjs.iterator();
					while(iter.hasNext()) {
						String temp = iter.next();
						if(variableToFile.get(temp) != null) {
							//This file is open
							openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(temp));
						}
					}
					if(variableToFile.get(prefix + "." + ins.getUse(1)) != null) {
						//This file is open
						openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(prefix + "." + ins.getUse(1)));
					}
				}
				if(cla.getName().toString().contains("Ljava/util/Scanner")) { //FileReader new
					//Get the file object it refers to
					//Either the variable itself or the object it could be is a file object
					Set<String> possibleObjs = typeGraph.getEdges(prefix + "." + ins.getUse(1));
					Iterator<String> iter = possibleObjs.iterator();
					while(iter.hasNext()) {
						String temp = iter.next();
						if(variableToFile.get(temp) != null) {
							//This file is open
							openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(temp));
						}
					}
					if(variableToFile.get(prefix + "." + ins.getUse(1)) != null) {
						//This file is open
						openSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(prefix + "." + ins.getUse(1)));
					}
				}
			}

			if(met.getName().toString().contains("close")) { 
				System.out.println("When the impostor sus");
				System.out.println(prefix + "." + ins.getUse(0));
				Set<String> possibleObjs = typeGraph.getEdges(prefix + "." + ins.getUse(0));
				Iterator<String> iter = possibleObjs.iterator();
				while(iter.hasNext()) {
					String temp = iter.next();
					// System.out.println(temp);
					if(variableToFile.get(temp) != null) {
						//This file is open
						// System.out.println("FileInputStream open");
						// System.out.println(variableToFile.get(temp));
						// System.out.println(prefix + "." + ins.iIndex());
						closeSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(temp));
					}
				}
				if(variableToFile.get(prefix + "." + ins.getUse(0)) != null) {
					//This file is open
					// System.out.println("FileInputStream open");
					// System.out.println(variableToFile.get(prefix + "." + ins.getUse(1)));
					// System.out.println(prefix + "." + ins.iIndex());
					closeSet.put("inst." + prefix + "." + ins.iIndex(), variableToFile.get(prefix + "." + ins.getUse(0)));
				}
			}
			
		}


		// System.out.println(ins.getCallSite());
		// MethodReference invokedMethod = ins.getDeclaredTarget();

		//lhs = o.m(a, ...)
		// o.m(a, ...)
		// o must be a local, parameter, or this

		// String receiver = (!ins.isStatic() && ins.getReceiver()==1)?"this":String.valueOf(ins.getReceiver());
		

		// //For each C.m that is the target of C.m[i] in the conservative call graph

		// //Add an edge from the representative of o to C.m.this
		// 	//if return type is not void
		// 		//add an edge from C.m.return to the representative for lhs
		// //For each argument a that has object type
		// 	//add an edge from the representative of ai to the rep of the matching parameter of C.m
		// // System.out.println(cg.getPossibleTargets(currNode, ins.getCallSite()));
		// for(CGNode cgnode : cg.getPossibleTargets(currNode, ins.getCallSite())) {
		// 	IR ir = cgnode.getIR();
		// 	IMethod met = ir.getMethod();
		// 	IClass cla = met.getDeclaringClass();
		// 	int i = 0;


		// 	// ShrikeClass shrikeKlass = (ShrikeClass) cla;
		// 	// ClassInstrumenter(shrikeKlass.getReader());

		// 	if(!ins.getCallSite().isStatic()) {
		// 		typeGraph.addEdge(prefix + "." + receiver, cla.getName().toString() + "." + met.getName().toString()+ ".this", false);
		// 		i = 1; //skip first parameter which is implicit this
		// 	}

		// 	if(ins.hasDef()) {
		// 		typeGraph.addEdge(cla.getName().toString() + "." + met.getName().toString()+ ".return", prefix + "." + ins.getDef(), false);
		// 	}

		// 	for(; i < ins.getNumberOfPositionalParameters(); i++) {

		// 		//System.out.println(prefix + "." + ins.getUse(i) + " /// " + cla.getName().toString() + "." + met.getName().toString()+ "." + (i+1));
		// 		if(met.getParameterType(i).isReferenceType()) {   
		// 			typeGraph.addEdge(prefix + "." + ins.getUse(i), cla.getName().toString() + "." + met.getName().toString()+ "." + (i+1), false);
		// 		}
		// 	}
		// }
		System.out.println(ins);
	}

	@Override
	public void visitLoadMetadata(SSALoadMetadataInstruction ins) {
	}

	@Override
	public void visitMonitor(SSAMonitorInstruction ins) {
	}

	@Override
	public void visitNew(SSANewInstruction ins) { 
		// if(ins.hasDef()) { //check for new FileInputStream, BufferedReader/Writer, FileReader/Writer, Scanner
		// 	String rhs = ins.getConcreteType().getName().toString();
		// 	typeGraph.addEdge(rhs, prefix + "." + ins.getDef(), false);
		// }
		// System.out.println("New---");
		System.out.println(ins);
	}

	@Override
	public void visitPhi(SSAPhiInstruction ins) { 
		System.out.println(ins);
	}

	@Override
	public void visitPi(SSAPiInstruction ins) { 
		System.out.println(ins);
	}

	@Override
	public void visitPut(SSAPutInstruction ins) { 
		System.out.println(ins);
	}

	@Override
	public void visitReturn(SSAReturnInstruction ins) {
		System.out.println(ins);
	}

	@Override
	public void visitSwitch(SSASwitchInstruction ins) {
	}

	@Override
	public void visitThrow(SSAThrowInstruction ins) {
	}

	@Override
	public void visitUnaryOp(SSAUnaryOpInstruction ins) {
	}

	public void doCommonCase(SSAInstruction ins) {

	}
}
