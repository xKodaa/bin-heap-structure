package bin_heap.structure;

import bin_heap.stack.AbstrFifo;
import bin_heap.stack.AbstrLifo;
import lombok.Getter;

import java.util.Iterator;

public class AbstrTable <K extends Comparable<K>, V> implements IAbstrTable<K,V> {
    private TreeNode root;

    public class TreeNode {
        @Getter
        private V value;
        private K key;
        private TreeNode left;
        private TreeNode right;

        public TreeNode(K key, V value) {
            this.value = value;
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }

    @Override
    public V najdi(K key) {
        if (jePrazdny()) { return null; }

        TreeNode tmp = root;
        for (; ;) {
            if (key.compareTo(tmp.key) == 0) {  // key se shoduje s tmp.key
                return tmp.value;
            } else if (key.compareTo(tmp.key) < 0) { // key je menší než tmp.key
                if (tmp.left != null) {
                    tmp = tmp.left;
                } else { return null; }

            } else if (key.compareTo(tmp.key) > 0) { // key je větší než tmp.key
                if (tmp.right != null) {
                    tmp = tmp.right;
                } else { return null; }
            }
        }
    }

    @Override
    public void vloz(K key, V value) {
        if (isValueNotNull(value) && isKeyNotPresentInTree(key)) {
            TreeNode treeNode = new TreeNode(key, value);
            if (jePrazdny()){
                root = treeNode;
                return;
            }
            TreeNode tmp = root;

            for (; ;) {
                if (key.compareTo(tmp.key) < 0) { // key je menší než tmp.key
                    if (tmp.left != null) {
                        tmp = tmp.left;
                    } else {
                        tmp.left = treeNode;
                        return;
                    }
                } else if (key.compareTo(tmp.key) > 0) { // key je větší než tmp.key
                    if (tmp.right != null) {
                        tmp = tmp.right;
                    } else {
                        tmp.right = treeNode;
                        return;
                    }
                }
            }
        } else {
            System.err.println("Vložení prvku se neprovedlo, klíč " + key + " je v tabulce již přítomen");
        }
    }

    @Override
    public V odeber(K key) {
        if (jePrazdny()) { return null; }
        V returnedData = null;
        TreeNode parrent = null;
        TreeNode current = root;

        while (current != null) {
            if (key.compareTo(current.key) < 0) { // key je menší než current.key
                parrent = current;
                current = current.left;
            } else if (key.compareTo(current.key) > 0) { // key je větší než current.key
                parrent = current;
                current = current.right;
            } else {
                // 1. možnost: odebirany prvek nemá potomky
                if (current.left == null && current.right == null) {
                    if (parrent == null) { // nemá potomky a ani předchudce -> byl to jediny uzel
                        root = null;
                    } else if (parrent.left == current) {   // prvek je levym potomkem
                        parrent.left = null;
                    } else {    // prvek je pravym potomkem
                        parrent.right = null;
                    }
                // 2. možnost: odebirany prvek má dva potomky
                } else if (current.left != null && current.right != null) {
                    TreeNode successorParrent = current;
                    TreeNode successor = current.right;

                    while (successor.left != null) { // najdi nejlevější uzel pravého podstromu
                        successorParrent = successor;
                        successor = successor.left;
                    }

                    current.key = successor.key;
                    current.value = successor.value;

                    if (successor == successorParrent.left) {   // prvek je levym potomkem
                        successorParrent.left = successor.right;
                    } else {    // prvek je pravym potomkem
                        successorParrent.right = successor.right;
                    }
                // 3. možnost: odebirany prvek má jednoho potomka
                } else {
                    if (current.left == null) { // má pouze pravého potomka
                        if (parrent == null) {  // odebíráme roota
                            root = current.right;
                        } else if (current == parrent.left) {   // prvek je levym potomkem
                            parrent.left = current.right;
                        } else {    // prvek je pravym potomkem
                            parrent.right = current.right;
                        }
                    } else {    // má pouze levého potomka
                        if (parrent == null) {  // odebíráme roota
                            root = current.left;
                        } else if (current == parrent.left) {   // prvek je levym potomkem
                            parrent.left = current.left;
                        } else {    // prvek je pravym potomkem
                            parrent.right = current.left;
                        }
                    }
                }
                returnedData = current.value;
                return returnedData;
            }
        }
        return returnedData;
    }

    @Override
    public Iterator<TreeNode> vytvorIterator(eTypProhl typ) {
        if (root == null) { return null; }
        TreeNode actual = root;

        if (typ == eTypProhl.HLOUBKA) {
            AbstrLifo<TreeNode> zasobnik = new AbstrLifo<>();
            while (actual != null || !zasobnik.jePrazdny()) {
                while (actual != null) {
                    zasobnik.vloz(actual);
                    actual = actual.left;
                }
                actual = zasobnik.odeber(); // backtrack (ukazatel se vrátí o uroveň výš)
                actual = actual.right;
            }
            return zasobnik.vytvorIterator();

        } else if (typ == eTypProhl.SIRKA) {
            AbstrFifo<TreeNode> fronta = new AbstrFifo<>();
            fronta.vloz(actual);
            while(!fronta.jePrazdny()) {
                TreeNode removedNode = fronta.odeber();
                // prohlidka "po vrstvach"
                if (removedNode.left != null) {
                    fronta.vloz(removedNode.left);
                }
                if (removedNode.right != null) {
                    fronta.vloz(removedNode.right);
                }
            }
            return fronta.vytvorIterator();
        }
        return null;
    }

    @Override
    public void zrus() {
        this.root = null;
    }

    @Override
    public boolean jePrazdny() {
        return root == null;
    }

    private boolean isKeyNotPresentInTree(K key) {
        return najdi(key) == null;
    }

    private boolean isValueNotNull(V value) {
        return value != null;
    }
}
