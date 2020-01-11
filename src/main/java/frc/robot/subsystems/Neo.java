package frc.robot.subsystems;

import frc.robot.Constants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import frc.robot.OI;

/**
 * Subsystem for the Neo Spark Max motor.
 * 
 * @see https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java
 * @see https://github.com/REVrobotics/SPARK-MAX-Examples/blob/master/Java/Tank%20Drive%20With%20CAN/src/main/java/frc/robot/Robot.java
 */
public class Neo extends SubsystemBase {

    private CANSparkMax m_sparkMax;
    private DifferentialDrive m_differentialDrive;

    public Neo() {

        // You need to register the subsystem to get it's periodic
        // method to be called by the Scheduler
        CommandScheduler.getInstance().registerSubsystem(this);

        m_sparkMax = new CANSparkMax(Constants.NEO_SPARK_MAX_CAN_ID, MotorType.kBrushless);
        m_sparkMax.restoreFactoryDefaults();

        m_differentialDrive = new DifferentialDrive(m_sparkMax, m_sparkMax);
    }

    @Override
    public void periodic() {
        // Put code here to be run every loop
        double fwdRevValue = OI.getXboxLeftJoystickY(); // -1.0 full forward, 1.0 full reverse
        double turnValue = OI.getXboxRightJoystickX();  // -1.0 counter clockwise, 1.0 clockwise

        // Was seeing a value of -0.023375 on the Right Joystick X value coming
        // from one of the Xbox Controllers when it was at it's 'zero' position.
        if ((turnValue < Constants.ARCADE_DRIVE_TURN_DEADBAND) && (turnValue > -Constants.ARCADE_DRIVE_TURN_DEADBAND)) {
            turnValue = 0.0;
        }

        System.out.println("fwdRevValue = " + fwdRevValue + " turnValue = " + turnValue);

        m_differentialDrive.arcadeDrive(fwdRevValue, -turnValue); 
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}
