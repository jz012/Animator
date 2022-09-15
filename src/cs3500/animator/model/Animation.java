package cs3500.animator.model;

/**
 * An enum that represents an animation type. This enum can be expanded by simply adding the
 * type, and then extending the AbstractAnimation class to implement the new type with this enum
 * as its field.
 */
public enum Animation {
  MOVE, COLORCHANGE, RESIZE
}