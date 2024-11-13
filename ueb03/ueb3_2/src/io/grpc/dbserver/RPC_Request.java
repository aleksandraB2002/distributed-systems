// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: DatabaseService.proto
// Protobuf Java Version: 4.28.3

package io.grpc.dbserver;

/**
 * Protobuf type {@code RPC_Request}
 */
public final class RPC_Request extends
    com.google.protobuf.GeneratedMessage implements
    // @@protoc_insertion_point(message_implements:RPC_Request)
    RPC_RequestOrBuilder {
private static final long serialVersionUID = 0L;
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 28,
      /* patch= */ 3,
      /* suffix= */ "",
      RPC_Request.class.getName());
  }
  // Use RPC_Request.newBuilder() to construct.
  private RPC_Request(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
    super(builder);
  }
  private RPC_Request() {
    operation_ = 0;
    record_ = "";
  }

  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return io.grpc.dbserver.DatabaseServiceOuterClass.internal_static_RPC_Request_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return io.grpc.dbserver.DatabaseServiceOuterClass.internal_static_RPC_Request_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            io.grpc.dbserver.RPC_Request.class, io.grpc.dbserver.RPC_Request.Builder.class);
  }

  private int bitField0_;
  public static final int OPERATION_FIELD_NUMBER = 1;
  private int operation_ = 0;
  /**
   * <code>.Operation operation = 1;</code>
   * @return The enum numeric value on the wire for operation.
   */
  @java.lang.Override public int getOperationValue() {
    return operation_;
  }
  /**
   * <code>.Operation operation = 1;</code>
   * @return The operation.
   */
  @java.lang.Override public io.grpc.dbserver.Operation getOperation() {
    io.grpc.dbserver.Operation result = io.grpc.dbserver.Operation.forNumber(operation_);
    return result == null ? io.grpc.dbserver.Operation.UNRECOGNIZED : result;
  }

  public static final int INDEX_FIELD_NUMBER = 2;
  private int index_ = 0;
  /**
   * <code>optional int32 Index = 2;</code>
   * @return Whether the index field is set.
   */
  @java.lang.Override
  public boolean hasIndex() {
    return ((bitField0_ & 0x00000001) != 0);
  }
  /**
   * <code>optional int32 Index = 2;</code>
   * @return The index.
   */
  @java.lang.Override
  public int getIndex() {
    return index_;
  }

  public static final int RECORD_FIELD_NUMBER = 3;
  @SuppressWarnings("serial")
  private volatile java.lang.Object record_ = "";
  /**
   * <code>optional string Record = 3;</code>
   * @return Whether the record field is set.
   */
  @java.lang.Override
  public boolean hasRecord() {
    return ((bitField0_ & 0x00000002) != 0);
  }
  /**
   * <code>optional string Record = 3;</code>
   * @return The record.
   */
  @java.lang.Override
  public java.lang.String getRecord() {
    java.lang.Object ref = record_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      record_ = s;
      return s;
    }
  }
  /**
   * <code>optional string Record = 3;</code>
   * @return The bytes for record.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getRecordBytes() {
    java.lang.Object ref = record_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      record_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (operation_ != io.grpc.dbserver.Operation.DEFAULT.getNumber()) {
      output.writeEnum(1, operation_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      output.writeInt32(2, index_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      com.google.protobuf.GeneratedMessage.writeString(output, 3, record_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (operation_ != io.grpc.dbserver.Operation.DEFAULT.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, operation_);
    }
    if (((bitField0_ & 0x00000001) != 0)) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(2, index_);
    }
    if (((bitField0_ & 0x00000002) != 0)) {
      size += com.google.protobuf.GeneratedMessage.computeStringSize(3, record_);
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof io.grpc.dbserver.RPC_Request)) {
      return super.equals(obj);
    }
    io.grpc.dbserver.RPC_Request other = (io.grpc.dbserver.RPC_Request) obj;

    if (operation_ != other.operation_) return false;
    if (hasIndex() != other.hasIndex()) return false;
    if (hasIndex()) {
      if (getIndex()
          != other.getIndex()) return false;
    }
    if (hasRecord() != other.hasRecord()) return false;
    if (hasRecord()) {
      if (!getRecord()
          .equals(other.getRecord())) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + OPERATION_FIELD_NUMBER;
    hash = (53 * hash) + operation_;
    if (hasIndex()) {
      hash = (37 * hash) + INDEX_FIELD_NUMBER;
      hash = (53 * hash) + getIndex();
    }
    if (hasRecord()) {
      hash = (37 * hash) + RECORD_FIELD_NUMBER;
      hash = (53 * hash) + getRecord().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static io.grpc.dbserver.RPC_Request parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  public static io.grpc.dbserver.RPC_Request parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input);
  }

  public static io.grpc.dbserver.RPC_Request parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input);
  }
  public static io.grpc.dbserver.RPC_Request parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessage
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(io.grpc.dbserver.RPC_Request prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessage.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code RPC_Request}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:RPC_Request)
      io.grpc.dbserver.RPC_RequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return io.grpc.dbserver.DatabaseServiceOuterClass.internal_static_RPC_Request_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return io.grpc.dbserver.DatabaseServiceOuterClass.internal_static_RPC_Request_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              io.grpc.dbserver.RPC_Request.class, io.grpc.dbserver.RPC_Request.Builder.class);
    }

    // Construct using io.grpc.dbserver.RPC_Request.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      bitField0_ = 0;
      operation_ = 0;
      index_ = 0;
      record_ = "";
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return io.grpc.dbserver.DatabaseServiceOuterClass.internal_static_RPC_Request_descriptor;
    }

    @java.lang.Override
    public io.grpc.dbserver.RPC_Request getDefaultInstanceForType() {
      return io.grpc.dbserver.RPC_Request.getDefaultInstance();
    }

    @java.lang.Override
    public io.grpc.dbserver.RPC_Request build() {
      io.grpc.dbserver.RPC_Request result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public io.grpc.dbserver.RPC_Request buildPartial() {
      io.grpc.dbserver.RPC_Request result = new io.grpc.dbserver.RPC_Request(this);
      if (bitField0_ != 0) { buildPartial0(result); }
      onBuilt();
      return result;
    }

    private void buildPartial0(io.grpc.dbserver.RPC_Request result) {
      int from_bitField0_ = bitField0_;
      if (((from_bitField0_ & 0x00000001) != 0)) {
        result.operation_ = operation_;
      }
      int to_bitField0_ = 0;
      if (((from_bitField0_ & 0x00000002) != 0)) {
        result.index_ = index_;
        to_bitField0_ |= 0x00000001;
      }
      if (((from_bitField0_ & 0x00000004) != 0)) {
        result.record_ = record_;
        to_bitField0_ |= 0x00000002;
      }
      result.bitField0_ |= to_bitField0_;
    }

    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof io.grpc.dbserver.RPC_Request) {
        return mergeFrom((io.grpc.dbserver.RPC_Request)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(io.grpc.dbserver.RPC_Request other) {
      if (other == io.grpc.dbserver.RPC_Request.getDefaultInstance()) return this;
      if (other.operation_ != 0) {
        setOperationValue(other.getOperationValue());
      }
      if (other.hasIndex()) {
        setIndex(other.getIndex());
      }
      if (other.hasRecord()) {
        record_ = other.record_;
        bitField0_ |= 0x00000004;
        onChanged();
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 8: {
              operation_ = input.readEnum();
              bitField0_ |= 0x00000001;
              break;
            } // case 8
            case 16: {
              index_ = input.readInt32();
              bitField0_ |= 0x00000002;
              break;
            } // case 16
            case 26: {
              record_ = input.readStringRequireUtf8();
              bitField0_ |= 0x00000004;
              break;
            } // case 26
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }
    private int bitField0_;

    private int operation_ = 0;
    /**
     * <code>.Operation operation = 1;</code>
     * @return The enum numeric value on the wire for operation.
     */
    @java.lang.Override public int getOperationValue() {
      return operation_;
    }
    /**
     * <code>.Operation operation = 1;</code>
     * @param value The enum numeric value on the wire for operation to set.
     * @return This builder for chaining.
     */
    public Builder setOperationValue(int value) {
      operation_ = value;
      bitField0_ |= 0x00000001;
      onChanged();
      return this;
    }
    /**
     * <code>.Operation operation = 1;</code>
     * @return The operation.
     */
    @java.lang.Override
    public io.grpc.dbserver.Operation getOperation() {
      io.grpc.dbserver.Operation result = io.grpc.dbserver.Operation.forNumber(operation_);
      return result == null ? io.grpc.dbserver.Operation.UNRECOGNIZED : result;
    }
    /**
     * <code>.Operation operation = 1;</code>
     * @param value The operation to set.
     * @return This builder for chaining.
     */
    public Builder setOperation(io.grpc.dbserver.Operation value) {
      if (value == null) {
        throw new NullPointerException();
      }
      bitField0_ |= 0x00000001;
      operation_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.Operation operation = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearOperation() {
      bitField0_ = (bitField0_ & ~0x00000001);
      operation_ = 0;
      onChanged();
      return this;
    }

    private int index_ ;
    /**
     * <code>optional int32 Index = 2;</code>
     * @return Whether the index field is set.
     */
    @java.lang.Override
    public boolean hasIndex() {
      return ((bitField0_ & 0x00000002) != 0);
    }
    /**
     * <code>optional int32 Index = 2;</code>
     * @return The index.
     */
    @java.lang.Override
    public int getIndex() {
      return index_;
    }
    /**
     * <code>optional int32 Index = 2;</code>
     * @param value The index to set.
     * @return This builder for chaining.
     */
    public Builder setIndex(int value) {

      index_ = value;
      bitField0_ |= 0x00000002;
      onChanged();
      return this;
    }
    /**
     * <code>optional int32 Index = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearIndex() {
      bitField0_ = (bitField0_ & ~0x00000002);
      index_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object record_ = "";
    /**
     * <code>optional string Record = 3;</code>
     * @return Whether the record field is set.
     */
    public boolean hasRecord() {
      return ((bitField0_ & 0x00000004) != 0);
    }
    /**
     * <code>optional string Record = 3;</code>
     * @return The record.
     */
    public java.lang.String getRecord() {
      java.lang.Object ref = record_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        record_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>optional string Record = 3;</code>
     * @return The bytes for record.
     */
    public com.google.protobuf.ByteString
        getRecordBytes() {
      java.lang.Object ref = record_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        record_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>optional string Record = 3;</code>
     * @param value The record to set.
     * @return This builder for chaining.
     */
    public Builder setRecord(
        java.lang.String value) {
      if (value == null) { throw new NullPointerException(); }
      record_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }
    /**
     * <code>optional string Record = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearRecord() {
      record_ = getDefaultInstance().getRecord();
      bitField0_ = (bitField0_ & ~0x00000004);
      onChanged();
      return this;
    }
    /**
     * <code>optional string Record = 3;</code>
     * @param value The bytes for record to set.
     * @return This builder for chaining.
     */
    public Builder setRecordBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) { throw new NullPointerException(); }
      checkByteStringIsUtf8(value);
      record_ = value;
      bitField0_ |= 0x00000004;
      onChanged();
      return this;
    }

    // @@protoc_insertion_point(builder_scope:RPC_Request)
  }

  // @@protoc_insertion_point(class_scope:RPC_Request)
  private static final io.grpc.dbserver.RPC_Request DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new io.grpc.dbserver.RPC_Request();
  }

  public static io.grpc.dbserver.RPC_Request getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<RPC_Request>
      PARSER = new com.google.protobuf.AbstractParser<RPC_Request>() {
    @java.lang.Override
    public RPC_Request parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<RPC_Request> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<RPC_Request> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public io.grpc.dbserver.RPC_Request getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

