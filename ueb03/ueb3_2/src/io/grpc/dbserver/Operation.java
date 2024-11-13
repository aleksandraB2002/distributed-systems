// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DatabaseService.proto
// Protobuf Java Version: 4.28.3

package io.grpc.dbserver;

/**
 * <pre>
 * Enum für jede Operation
 * </pre>
 *
 * Protobuf enum {@code Operation}
 */
public enum Operation
    implements com.google.protobuf.ProtocolMessageEnum {
  /**
   * <pre>
   * Standardwert laut proto3 Style-Standards
   * </pre>
   *
   * <code>DEFAULT = 0;</code>
   */
  DEFAULT(0),
  /**
   * <code>GET_RECORD = 1;</code>
   */
  GET_RECORD(1),
  /**
   * <code>ADD_RECORD = 2;</code>
   */
  ADD_RECORD(2),
  /**
   * <code>GET_SIZE = 3;</code>
   */
  GET_SIZE(3),
  UNRECOGNIZED(-1),
  ;

  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 3,
      /* suffix= */ "",
      Operation.class.getName());
  }
  /**
   * <pre>
   * Standardwert laut proto3 Style-Standards
   * </pre>
   *
   * <code>DEFAULT = 0;</code>
   */
  public static final int DEFAULT_VALUE = 0;
  /**
   * <code>GET_RECORD = 1;</code>
   */
  public static final int GET_RECORD_VALUE = 1;
  /**
   * <code>ADD_RECORD = 2;</code>
   */
  public static final int ADD_RECORD_VALUE = 2;
  /**
   * <code>GET_SIZE = 3;</code>
   */
  public static final int GET_SIZE_VALUE = 3;


  public final int getNumber() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalArgumentException(
          "Can't get the number of an unknown enum value.");
    }
    return value;
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   * @deprecated Use {@link #forNumber(int)} instead.
   */
  @java.lang.Deprecated
  public static Operation valueOf(int value) {
    return forNumber(value);
  }

  /**
   * @param value The numeric wire value of the corresponding enum entry.
   * @return The enum associated with the given numeric wire value.
   */
  public static Operation forNumber(int value) {
    switch (value) {
      case 0: return DEFAULT;
      case 1: return GET_RECORD;
      case 2: return ADD_RECORD;
      case 3: return GET_SIZE;
      default: return null;
    }
  }

  public static com.google.protobuf.Internal.EnumLiteMap<Operation>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static final com.google.protobuf.Internal.EnumLiteMap<
      Operation> internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<Operation>() {
          public Operation findValueByNumber(int number) {
            return Operation.forNumber(number);
          }
        };

  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    if (this == UNRECOGNIZED) {
      throw new java.lang.IllegalStateException(
          "Can't get the descriptor of an unrecognized enum value.");
    }
    return getDescriptor().getValues().get(ordinal());
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return io.grpc.dbserver.DatabaseServiceOuterClass.getDescriptor().getEnumTypes().get(0);
  }

  private static final Operation[] VALUES = values();

  public static Operation valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    if (desc.getIndex() == -1) {
      return UNRECOGNIZED;
    }
    return VALUES[desc.getIndex()];
  }

  private final int value;

  private Operation(int value) {
    this.value = value;
  }

  // @@protoc_insertion_point(enum_scope:Operation)
}

