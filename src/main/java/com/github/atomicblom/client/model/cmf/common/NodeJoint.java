package com.github.atomicblom.client.model.cmf.common;

import com.google.common.base.Objects;
import com.google.common.base.Optional;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.model.animation.IJoint;
import javax.vecmath.Matrix4f;

/**
 * Created by codew on 24/03/2016.
 */
final class NodeJoint implements IJoint
{
    private final Node<?> node;

    public NodeJoint(Node<?> node)
    {
        this.node = node;
    }

    @Override
    public TRSRTransformation getInvBindPose()
    {
        Matrix4f m = node.getTransformation().getMatrix();
        m.invert();
        TRSRTransformation pose = new TRSRTransformation(m);

        if (node.getParent() != null)
        {
            TRSRTransformation parent = new NodeJoint(node.getParent()).getInvBindPose();
            pose = pose.compose(parent);
        }
        return pose;
    }

    @Override
    public Optional<NodeJoint> getParent()
    {
        // FIXME cache?
        if (node.getParent() == null) return Optional.absent();
        return Optional.of(new NodeJoint(node.getParent()));
    }

    public Node<?> getNode()
    {
        return node;
    }

    @Override
    public int hashCode()
    {
        return node.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        NodeJoint other = (NodeJoint) obj;
        return Objects.equal(node, other.node);
    }
}
