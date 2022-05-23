
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
import com.ibm.wala.shrikeCT.ClassReader;
import com.ibm.wala.shrikeBT.shrikeCT.ClassInstrumenter;
import com.ibm.wala.shrikeBT.MethodData;
import com.ibm.wala.shrike.shrikeBT.Util;
import com.ibm.wala.shrike.shrikeBT.IInstruction;
import com.ibm.wala.shrike.shrikeBT.Instruction;

public class ResourceClose {
	public static void closeResource(CGNode cg, SSAInstruction ins) {
		IR ir = cg.getIR();
		IMethod met = ir.getMethod();
		IClass cla = met.getDeclaringClass();

		ShrikeClass shrikeKlass = (ShrikeClass) cla;
		ClassReader reader = shrikeKlass.getReader();
		ClassInstrumenter ci = new ClassInstrumenter("", reader, null);


		System.out.println(met.getName());
		for (int m = 0; m < ci.getReader().getMethodCount(); m++) {
			try {
				MethodData d = ci.visitMethod(m);
				if(d != null && d.getName() == met.getName()) {
					MethodEditor me = new MethodEditor(d);
					me.beginPass();
					me.insertAfterBody(
						new MethodEditor.Patch() {
							@Override
							public void emitTo(MethodEditor.Output w) {
							 w.emit(Util.makeInvoke(PrintStream.class, "close", new Class[] {}));
							}
						});
					me.applyPatches();
				}
			} catch (Exception e)  
			{  
				e.printStackTrace();  
			} 
		}
		// System.out.println(shrikeKlass);
		// System.out.println(reader);
	}
	
}
