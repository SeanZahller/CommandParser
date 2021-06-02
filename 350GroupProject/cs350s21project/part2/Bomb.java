package cs350s21project.part2;
import java.util.Random;

// ====================================================================================================================================================================================================
/**
 * Defines a dumb bomb that descends from a release point in three-dimensional space. Wind may influence the descent such that the impact point differs from the release point. Inherent targeting
 * error may also influence the impact point.
 * <p>
 * The coordinate system is the same as in Task 3. All distances and altitudes are in meters. Angles are in compass degrees. Time is in seconds. The ground is at sea level.
 */
public class Bomb
{
   private final Coordinates _releaseCoordinates;
   private final double _releaseAltitude;

   private final double _descentSpeed;

   private final E_ErrorType _errorType;
   private final double _errorRange;

   private final double _windDirection;
   private final double _windSpeed;

   private final Random _random = new Random(System.currentTimeMillis());

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Creates a bomb.
    *
    * @param releaseX        - the release <i>x</i> coordinate
    * @param releaseY        - the release <i>y</i> coordinate
    * @param releaseAltitude - the release altitude
    * @param descentSpeed    - the speed at which the bomb descends
    * @param errorType       - the type of targeting error
    * @param errorRange      - the maximum range of the targeting error
    * @param windDirection   - the wind direction (from)
    * @param windSpeed       - the wind speed
    */
   public Bomb(final double releaseX, final double releaseY, final double releaseAltitude, final double descentSpeed, final E_ErrorType errorType, final double errorRange,
               final double windDirection, final double windSpeed)
   {
      _releaseCoordinates = new Coordinates(releaseX, releaseY);

      _releaseAltitude = releaseAltitude;

      _descentSpeed = descentSpeed;

      _errorType = errorType;
      _errorRange = errorRange;

      _windDirection = windDirection;
      _windSpeed = windSpeed;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Drops the bomb as specified in the writeup.
    *
    * @return the coordinates where the bomb impacted
    */
   public Coordinates drop()
   {
      // calculate the total time to descend
      double descentTimeSeconds = (_releaseAltitude / _descentSpeed);

      // calculate the horizontal distance the wind shifted the bomb during descent
      double windOffset = (_windSpeed * descentTimeSeconds);

      // calculate the direction the wind shifted the bomb
      double windDirectionRadians = Math.toRadians(90 + _windDirection);

      double windOffsetX = (Math.cos(windDirectionRadians) * windOffset);
      double windOffsetY = (Math.sin(windDirectionRadians) * windOffset);

      // calculate where the bomb would impact based on the wind error only
      double impactExpectedX = (_releaseCoordinates._x + windOffsetX);
      double impactExpectedY = (_releaseCoordinates._y + windOffsetY);

      // calculate the distance of the targeting error
      double errorRangeActual = 0;

      switch (_errorType)
      {
         case NONE:

            break;

         case UNIFORM:

            errorRangeActual = (_random.nextDouble() * _errorRange);

            break;

         case GAUSSIAN:

            errorRangeActual = (Math.abs(_random.nextGaussian()) * _errorRange);

            break;

         default:

            throw new RuntimeException("unknown error type " + _errorType);
      }

      // calculate the angle of the targeting error
      double errorAngle = (Math.random() * 360);
      double errorAngleRadians = Math.toRadians(errorAngle);

      // calculate the direction the targeting error shifted the bomb
      double errorOffsetX = (Math.cos(errorAngleRadians) * errorRangeActual);
      double errorOffsetY = (Math.sin(errorAngleRadians) * errorRangeActual);

      // calculate where the bomb would impact based on the targeting error (which is based on the wind error)
      double impactActualX = (impactExpectedX + errorOffsetX);
      double impactActualY = (impactExpectedY + errorOffsetY);

      // return the impact coordinates
      Coordinates coordinatesImpact = new Coordinates(impactActualX, impactActualY);

      return coordinatesImpact;
   }

   // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Returns the string representation of this object. This is not required.
    *
    * @return the representation
    */
   @Override
   public String toString()
   {
      return ("Bomb{releaseCoordinates=" + _releaseCoordinates + " releaseAltitude=" + _releaseAltitude + " descentSpeed=" + _descentSpeed + " errorType=" + _errorType + " errorRange=" + _errorRange +
            " windDirection=" + _windDirection + " windSpeed=" + _windSpeed + '}');
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the speed at which the bomb descends.
    *
    * @return the speed
    */
   public double getDescentSpeed()
   {
      return _descentSpeed;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the maximum range of the targeting error.
    *
    * @return the range
    */
   public double getErrorRange()
   {
      return _errorRange;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the type of targeting error.
    *
    * @return the error
    */
   public E_ErrorType getErrorType()
   {
      return _errorType;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the release altitude.
    *
    * @return the altitude
    */
   public double getReleaseAltitude()
   {
      return _releaseAltitude;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the release coordinates.
    *
    * @return the coordinates
    */
   public Coordinates getReleaseCoordinates()
   {
      return _releaseCoordinates;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the wind direction (from).
    *
    * @return the direction
    */
   public double getWindDirection()
   {
      return _windDirection;
   }

   // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /**
    * Gets the wind speed.
    *
    * @return the speed
    */
   public double getWindSpeed()
   {
      return _windSpeed;
   }

   // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   /**
    * Defines the type of random targeting error.
    */
   public enum E_ErrorType
   {
      /**
       * uniformly distributed error
       */
      NONE,

      /**
       * uniformly distributed error
       */
      UNIFORM,

      /**
       * Gaussian (bell curve) distributed error
       */
      GAUSSIAN
   }

   // = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =
   /**
    * Defines an arbitrary (<i>x</i>,<i>y</i>) coordinate pair.
    */
   public class Coordinates
   {
      private final double _x;
      private final double _y;

      // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      /**
       * Creates a coordinates object.
       *
       * @param x - the <i>x</i> coordinate
       * @param y - the <i>y</i> coordinate
       */
      public Coordinates(double x, double y)
      {
         _x = x;
         _y = y;
      }

      // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      /**
       * Gets the <i>x</i> coordinate
       *
       * @return the coordinate
       */
      public double getX()
      {
         return _x;
      }

      // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      /**
       * Gets the <i>y</i> coordinate.
       *
       * @return the coordinate
       */
      public double getY()
      {
         return _y;
      }

      // ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
      /**
       * Returns the string representation of this object. This is not required.
       *
       * @return the representation
       */
      @Override
      public String toString()
      {
         return (_x + "," + _y);
      }
   }
}
