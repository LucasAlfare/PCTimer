package com.fltimer.gui.swing.layout;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Layout manager that lays the components relatively to each other.
 */
public class JRelativeLayout implements LayoutManager2 {

    /**
     * A basic lookup table for the defined constraints.
     */
    private final HashMap<Component, Constraints> table;

    /**
     * Creates a new instance of a JRelativeLayout manager.
     */
    public JRelativeLayout() {
        table = new HashMap<>();
    }

    /**
     * Helper method to supply a new raw instance of the constraints holder class.
     *
     * @return a new instance of the constraints holder class.
     */
    public static Constraints relativeConstraints() {
        return new JRelativeLayout.Constraints();
    }

    /**
     * Called always that a component adds another component inside itself, since the calling component has a JRelativeLayout as the main layout manager.
     * <p>
     * Throws a exception if the {@code constraints} object is not a instance of {@link JRelativeLayout.Constraints} class.
     *
     * @param comp        The component that is being added.
     * @param constraints The "instructions" that should be attached to {@code comp}.
     */
    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints != null) {
            if (constraints instanceof Constraints) {
                table.put(comp, (Constraints) constraints);
            } else {
                throw new IllegalArgumentException(
                        "Unable to add some components to this layout with the supplied constraints. The constraints must to be a instance of a JRelativeLayout.Constraints class!");
            }
        } else {
            table.put(comp, relativeConstraints());
        }
    }

    /**
     * Performs all the calculations for all the components that was added in the {@code parent} container based in the defined constraints for each of then.
     * <p>
     * Is important to know that laying will happen in the following order:
     * <p>
     * - First are handled all sizing constraints;
     * <p>
     * - Second are handled all positioning constraints;
     * <p>
     * - Last are handled all numeric position constraints that affects previous positioning (e.g. margins/paddings).
     * <p>
     * Finally, after positioning the components, the algorithm attempts to fix the component size if it was turned out of bounds from its parent.
     * <p>
     * TODO: add checkings if component is a container/{@link JPanel} in order to define container size automatically of not manually set by {@code percentileWidth(double value)} or {@code percentileHeight(double value)}
     *
     * @param parent The root container that has a {@link JRelativeLayout} manager working.
     */
    @Override
    public void layoutContainer(Container parent) {
        if (parent.getLayout() instanceof JRelativeLayout) {
            for (Component component : parent.getComponents()) {
                final Constraints constraints = table.get(component);
                if (constraints != null) {
                    int x = component.getX(), y = component.getY(), width = component.getPreferredSize().width, height = component.getPreferredSize().height;
                    boolean isWidthSet = false, isHeightSet = false;

                    // handles sizing constraints
                    if (constraints.percentileWidth >= 0D) {
                        if (constraints.percentileWidth == Constraints.MATCH_PARENT) {
                            width = parent.getWidth();
                        } else {
                            width = (int) (parent.getWidth() * constraints.percentileWidth / 100D);
                        }
                        isWidthSet = true;
                    }

                    if (constraints.percentileHeight >= 0D) {
                        if (constraints.percentileHeight == Constraints.MATCH_PARENT) {
                            height = parent.getHeight();
                        } else {
                            height = (int) (parent.getHeight() * constraints.percentileHeight / 100D);
                        }
                        isHeightSet = true;
                    }

                    // handles boolean constraints
                    if (constraints.centerInParent) {
                        x = (parent.getWidth() / 2) - (width / 2);
                        y = (parent.getHeight() / 2) - (height / 2);
                    }

                    if (constraints.centerHorizontal) x = (parent.getWidth() / 2) - (width / 2);
                    if (constraints.centerVertical) y = (parent.getHeight() / 2) - (height / 2);
                    if (constraints.parentTop) y = 0;
                    if (constraints.parentBottom) y = parent.getHeight() - height;
                    if (constraints.parentStart) x = 0;
                    if (constraints.parentEnd) x = parent.getWidth() - width;

                    // handles component relative constraint
                    if (constraints.start != null) x = constraints.start.getX();
                    if (constraints.end != null)
                        x = constraints.end.getX() + (Math.abs(width - constraints.end.getWidth()));
                    if (constraints.top != null) y = constraints.top.getY();
                    if (constraints.bottom != null)
                        y = (constraints.bottom.getY() + constraints.bottom.getHeight()) - height;
                    if (constraints.above != null) y = constraints.above.getY() - height;
                    if (constraints.below != null) y = constraints.below.getY() + constraints.below.getHeight();
                    if (constraints.endOf != null) x = constraints.endOf.getX() + constraints.endOf.getWidth();
                    if (constraints.leftOf != null) x = constraints.leftOf.getX() - width;

                    // handles numeric constraints
                    if (constraints.marginTop >= 0) y += constraints.marginTop;
                    if (constraints.marginBottom >= 0) y -= constraints.marginBottom;
                    if (constraints.marginStart >= 0) x += constraints.marginStart;
                    if (constraints.marginEnd >= 0) x -= constraints.marginEnd;

                    // fix dimensions if the component become out of parent bounds todo: check if this is really necessary
                    if (isWidthSet) {
                        if (width + x > parent.getWidth()) {
                            width = parent.getWidth();
                        }
                    }

                    if (isHeightSet) {
                        if (height + y > parent.getHeight()) {
                            height = parent.getHeight();
                        }
                    }

                    // after all calculations, updates the bounds of the current component
                    component.setBounds(x, y, width, height);
                }
            }
        }
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        /* pass */
    }

    @Override
    public void invalidateLayout(Container target) {
        /* pass */
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0; // is this value indicating a "global margin" for the layout bounds?
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        table.remove(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return parent.getSize();
    }

    /**
     * This is a holder class to all the possible values that a component can have while being relatively managed.
     * <p>
     * Instances of this class should be passed every time a component is added to a container in order to specify its positions behavior when using a {@link JRelativeLayout} layout manager for this.
     * <p>
     * Also, this class implements a simple <b>builder</b> pattern to help define those its constraints values.
     * <p>
     * TODO: implement "remaining" sizing constraints. E.g.: width = the remaining free space of the parent.
     */
    @SuppressWarnings("unused")
    public static class Constraints {

        /**
         * Basic helper constant to indicate the component should have the same size of the parent component/container.
         */
        public static final double MATCH_PARENT = 100;

        /**
         * Values that holds sizes for the targeted component. The values will be treated as percentiles related to the parent component/container. E.g.: value 20 means 20% of the parent component/container size (if it is 100 pixels, then the layout manager will adjust component size to have only 20 pixels).
         */
        private double percentileWidth = -1, percentileHeight = -1;

        /**
         * All the possible boolean constraints indicating {@code true} or {@code false}.
         */
        private boolean
                centerInParent,
                centerVertical,
                centerHorizontal,
                parentTop,
                parentBottom,
                parentStart,
                parentEnd;

        /**
         * All the possible relative constraints that holds the specific component that works as relative point when laying out.
         */
        private Component
                start,
                end,
                top,
                bottom,
                above,
                below,
                endOf,
                leftOf;

        /**
         * All the numeric constraints that affects the previous definitions by incrementing it. These constraints affects positioning.
         */
        private int
                marginTop,
                marginBottom,
                marginEnd,
                marginStart;

        public Constraints percentileWidth(double value) {
            percentileWidth = value;
            return this;
        }

        public Constraints percentileHeight(double value) {
            percentileHeight = value;
            return this;
        }

        public Constraints centerInParent() {
            return centerInParent(true);
        }

        public Constraints centerInParent(boolean value) {
            centerInParent = value;
            return this;
        }

        public Constraints centerVertical() {
            return centerVertical(true);
        }

        public Constraints centerVertical(boolean value) {
            centerVertical = value;
            return this;
        }

        public Constraints centerHorizontal() {
            return centerHorizontal(true);
        }

        public Constraints centerHorizontal(boolean value) {
            centerHorizontal = value;
            return this;
        }

        public Constraints parentTop() {
            return parentTop(true);
        }

        public Constraints parentTop(boolean value) {
            parentTop = value;
            return this;
        }

        public Constraints parentBottom() {
            return parentBottom(true);
        }

        public Constraints parentBottom(boolean value) {
            parentBottom = value;
            return this;
        }

        public Constraints parentStart() {
            return parentStart(true);
        }

        public Constraints parentStart(boolean value) {
            parentStart = value;
            return this;
        }

        public Constraints parentEnd() {
            return parentEnd(true);
        }

        public Constraints parentEnd(boolean value) {
            parentEnd = value;
            return this;
        }

        public Constraints start(Component c) {
            start = c;
            return this;
        }

        public Constraints end(Component c) {
            end = c;
            return this;
        }

        public Constraints top(Component c) {
            top = c;
            return this;
        }

        public Constraints bottom(Component c) {
            bottom = c;
            return this;
        }

        public Constraints above(Component c) {
            above = c;
            return this;
        }

        public Constraints below(Component c) {
            below = c;
            return this;
        }

        public Constraints endOf(Component c) {
            endOf = c;
            return this;
        }

        public Constraints leftOf(Component c) {
            leftOf = c;
            return this;
        }

        public Constraints marginTop(int value) {
            marginTop = value;
            return this;
        }

        public Constraints marginBottom(int value) {
            marginBottom = value;
            return this;
        }

        public Constraints marginEnd(int value) {
            marginEnd = value;
            return this;
        }

        public Constraints marginStart(int value) {
            marginStart = value;
            return this;
        }
    }
}