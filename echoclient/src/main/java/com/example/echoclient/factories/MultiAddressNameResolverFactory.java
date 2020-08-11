package com.example.echoclient.factories;

import io.grpc.Attributes;
import io.grpc.EquivalentAddressGroup;
import io.grpc.NameResolver;

import javax.annotation.Nullable;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MultiAddressNameResolverFactory extends NameResolver.Factory {

    final List<EquivalentAddressGroup> addresses;

    public MultiAddressNameResolverFactory(SocketAddress... addresses) {
        this.addresses = Arrays.stream(addresses)
                .map(EquivalentAddressGroup::new)
                .collect(Collectors.toList());
    }

    @Nullable
    @Override
    public NameResolver newNameResolver(URI uri, Attributes attributes) {
        return new NameResolver() {
            @Override
            public String getServiceAuthority() {
                return "Vks Authority";
            }

            @Override
            public void start(Listener listener) {
                listener.onAddresses(addresses, Attributes.EMPTY);
            }

            @Override
            public void shutdown() {

            }
        };
    }

    @Override
    public String getDefaultScheme() {
        return "multiaddress";
    }
}

