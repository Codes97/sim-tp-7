package utn.frc.sim.generators.distributions;

import org.apache.commons.math3.distribution.NormalDistribution;
import utn.frc.sim.generators.distributions.valuegenerator.DistributionValueGenerator;

import static java.lang.Math.*;
import static java.lang.StrictMath.sqrt;

public class ConstantDistributionGenerator extends BaseRandomGenerator {

    private double constant;
    private ConstantDistributionGenerator(double constant) {
        this.constant = constant;
    }
    public static ConstantDistributionGenerator createOf(double constant) {
        return new ConstantDistributionGenerator(constant);
    }

    @Override
    public double random() {
        return constant;
    }

    @Override
    public DistributionValueGenerator getDistribution() {
        return new ConstantValueGenerator();
    }

    private class ConstantValueGenerator implements DistributionValueGenerator {

        @Override
        public double getExpectedFrequency(double from, double to) {
            return constant;
        }
    }
}
