package nz.ac.wgtn.veracity.spikes.provenanced.instrumentation;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.AsmVisitorWrapper;
import net.bytebuddy.description.ModifierReviewable;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import java.lang.reflect.Executable;
import static net.bytebuddy.matcher.ElementMatchers.*;
import static net.bytebuddy.matcher.ElementMatchers.not;

/**
 * Instrumentation without using the javaagent parameter.
 * @author jens dietrich
 */
public class DynamicInstrumentation {

    static {
        ByteBuddyAgent.install();
    }
    public static void transform(Class clazz) {

        System.out.println("transforming: " + clazz);
        ClassReloadingStrategy classReloadingStrategy = ClassReloadingStrategy.fromInstalledAgent(ClassReloadingStrategy.Strategy.REDEFINITION);
        new ByteBuddy()
            .redefine(clazz)
            .visit(MethodInterception.VISITOR)
            .make()
            .load(clazz.getClassLoader(), classReloadingStrategy);
    }

    public static class MethodInterception {

        public static final AsmVisitorWrapper VISITOR = Advice.to(MethodInterception.class)
            .on(any()
                    .and(not(isTypeInitializer()))
                    .and(not(ModifierReviewable.ForMethodDescription::isNative))
                    .and(not(ModifierReviewable.ForMethodDescription::isSynthetic))
            );

        @Advice.OnMethodEnter
        public static void onEntry(@Advice.Origin Executable method)  {
            ProvenanceStore.add(method.getDeclaringClass() + "::" + method.getName());
        }
    }

}
