package nz.ac.wgtn.veracity.spikes.provenanced.commons;

import java.util.Objects;

public class Client {
    private int age = 18;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return age == client.age;
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}
