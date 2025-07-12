package net.alan.ae.util;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.util.function.UnaryOperator;

import net.alan.ae.Ae;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AeIdentifier implements Comparable<Identifier> {
    public static final Codec<AeIdentifier> CODEC;
    public static final PacketCodec<ByteBuf, AeIdentifier> PACKET_CODEC;
    public static final SimpleCommandExceptionType COMMAND_EXCEPTION;
    public static final char NAMESPACE_SEPARATOR = ':';
    public static final String DEFAULT_NAMESPACE = Ae.MOD_ID;
    public final String namespace;
    public final String path;

    public AeIdentifier(String namespace, String path) {
        assert isNamespaceValid(namespace);

        assert isPathValid(path);

        this.namespace = namespace;
        this.path = path;
    }

    public static AeIdentifier ofValidated(String namespace, String path) {
        return new AeIdentifier(validateNamespace(namespace, path), AedatePath(namespace, path));
    }

    public static AeIdentifier of(String namespace, String path) {
        return ofValidated(namespace, path);
    }

    public static AeIdentifier of(String id) {
        return splitOn(id, ':');
    }

    public static AeIdentifier ofAe(String path) {
        return new AeIdentifier(DEFAULT_NAMESPACE, AedatePath(DEFAULT_NAMESPACE, path));
    }

    @Nullable
    public static AeIdentifier tryParse(String id) {
        return trySplitOn(id, ':');
    }

    @Nullable
    public static AeIdentifier tryParse(String namespace, String path) {
        return isNamespaceValid(namespace) && isPathValid(path) ? new AeIdentifier(namespace, path) : null;
    }

    public static AeIdentifier splitOn(String id, char delimiter) {
        int i = id.indexOf(delimiter);
        if (i >= 0) {
            String string = id.substring(i + 1);
            if (i != 0) {
                String string2 = id.substring(0, i);
                return ofValidated(string2, string);
            } else {
                return ofAe(string);
            }
        } else {
            return ofAe(id);
        }
    }

    @Nullable
    public static AeIdentifier trySplitOn(String id, char delimiter) {
        int i = id.indexOf(delimiter);
        if (i >= 0) {
            String string = id.substring(i + 1);
            if (!isPathValid(string)) {
                return null;
            } else if (i != 0) {
                String string2 = id.substring(0, i);
                return isNamespaceValid(string2) ? new AeIdentifier(string2, string) : null;
            } else {
                return new AeIdentifier("minecraft", string);
            }
        } else {
            return isPathValid(id) ? new AeIdentifier("minecraft", id) : null;
        }
    }

    public static DataResult<AeIdentifier> validate(String id) {
        try {
            return DataResult.success(of(id));
        } catch (InvalidIdentifierException invalidIdentifierException) {
            return DataResult.error(() -> "Not a valid resource location: " + id + " " + invalidIdentifierException.getMessage());
        }
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public AeIdentifier withPath(String path) {
        return new AeIdentifier(this.namespace, AedatePath(this.namespace, path));
    }

    public AeIdentifier withPath(UnaryOperator<String> pathFunction) {
        return this.withPath((String)pathFunction.apply(this.path));
    }

    public AeIdentifier withPrefixedPath(String prefix) {
        return this.withPath(prefix + this.path);
    }

    public AeIdentifier withSuffixedPath(String suffix) {
        return this.withPath(this.path + suffix);
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof AeIdentifier)) {
            return false;
        } else {
            AeIdentifier aeidentifier = (AeIdentifier)o;
            return this.namespace.equals(aeidentifier.namespace) && this.path.equals(aeidentifier.path);
        }
    }

    public int hashCode() {
        return 31 * this.namespace.hashCode() + this.path.hashCode();
    }

    public int compareTo(AeIdentifier identifier) {
        int i = this.path.compareTo(identifier.path);
        if (i == 0) {
            i = this.namespace.compareTo(identifier.namespace);
        }

        return i;
    }

    public String toUnderscoreSeparatedString() {
        return this.toString().replace('/', '_').replace(':', '_');
    }

    public String toTranslationKey() {
        return this.namespace + "." + this.path;
    }

    public String toShortTranslationKey() {
        return this.namespace.equals("minecraft") ? this.path : this.toTranslationKey();
    }

    public String toTranslationKey(String prefix) {
        return prefix + "." + this.toTranslationKey();
    }

    public String toTranslationKey(String prefix, String suffix) {
        return prefix + "." + this.toTranslationKey() + "." + suffix;
    }

    public static String readString(StringReader reader) {
        int i = reader.getCursor();

        while(reader.canRead() && isCharValid(reader.peek())) {
            reader.skip();
        }

        return reader.getString().substring(i, reader.getCursor());
    }

    public static AeIdentifier fromCommandInput(StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();
        String string = readString(reader);

        try {
            return of(string);
        } catch (InvalidIdentifierException var4) {
            reader.setCursor(i);
            throw COMMAND_EXCEPTION.createWithContext(reader);
        }
    }

    public static AeIdentifier fromCommandInputNonEmpty(StringReader reader) throws CommandSyntaxException {
        int i = reader.getCursor();
        String string = readString(reader);
        if (string.isEmpty()) {
            throw COMMAND_EXCEPTION.createWithContext(reader);
        } else {
            try {
                return of(string);
            } catch (InvalidIdentifierException var4) {
                reader.setCursor(i);
                throw COMMAND_EXCEPTION.createWithContext(reader);
            }
        }
    }

    public static boolean isCharValid(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
    }

    public static boolean isPathValid(String path) {
        for(int i = 0; i < path.length(); ++i) {
            if (!isPathCharacterValid(path.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static boolean isNamespaceValid(String namespace) {
        for(int i = 0; i < namespace.length(); ++i) {
            if (!isNamespaceCharacterValid(namespace.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public static String validateNamespace(String namespace, String path) {
        if (!isNamespaceValid(namespace)) {
            throw new InvalidIdentifierException("Non [a-z0-9_.-] character in namespace of location: " + namespace + ":" + path);
        } else {
            return namespace;
        }
    }

    public static boolean isPathCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '/' || character == '.';
    }

    public static boolean isNamespaceCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }

    public static String AedatePath(String namespace, String path) {
        if (!isPathValid(path)) {
            throw new InvalidIdentifierException("Non [a-z0-9/._-] character in path of location: " + namespace + ":" + path);
        } else {
            return path;
        }
    }

    static {
        CODEC = Codec.STRING.comapFlatMap(AeIdentifier::validate, AeIdentifier::toString).stable();
        PACKET_CODEC = PacketCodecs.STRING.xmap(AeIdentifier::of, AeIdentifier::toString);
        COMMAND_EXCEPTION = new SimpleCommandExceptionType(Text.translatable("argument.id.invalid"));
    }

    @Override
    public int compareTo(@NotNull Identifier o) {
        return 0;
    }
}