class Solution {
    
    static class UnionFind {
        int[] rank;
        int[] parent;
        
        UnionFind(int size) {
            rank = new int[size];
            parent = new int[size];
            for(int i = 1; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }
        
        private int find(int node) {
            if(node != parent[node]) {
                parent[node] = find(parent[node]);
            }
            return parent[node];
        }
        
        public boolean union(int n1, int n2) {
            int p1 = find(n1);
            int p2 = find(n2);
            if(p1 == p2) {
                return false;
            }
            if(rank[p1] >= rank[p2]) {
                parent[p2] = p1;
                rank[p1] += rank[p2];
            } else {
                parent[p1] = p2;
                rank[p2] += rank[p1];
            }
            return true;
        }
    }
    
    public int[] findRedundantConnection(int[][] edges) {
        int[] result = new int[]{};
        UnionFind uf = new UnionFind(edges.length + 1);
        
        for(int[] edge : edges) {
            if(!uf.union(edge[0], edge[1])) {
                result = edge;
            }
        }
        return result;
    }
}
