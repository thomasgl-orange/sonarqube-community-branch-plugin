/*
 * Copyright (C) 2019 Michael Clarke
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */
package com.github.mc1arke.sonarqube.plugin.ce.pullrequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.annotation.CheckForNull;

import org.sonar.api.rules.RuleType;
import org.sonar.ce.task.projectanalysis.component.Component;
import org.sonar.ce.task.projectanalysis.issue.IssueVisitor;
import org.sonar.core.issue.DefaultIssue;

public class PostAnalysisIssueVisitor extends IssueVisitor {

    private final List<ComponentIssue> collectedIssues = new ArrayList<>();

    @Override
    public void onIssue(Component component, DefaultIssue defaultIssue) {
        collectedIssues.add(new ComponentIssue(component, defaultIssue));
    }

    public List<ComponentIssue> getIssues() {
        return Collections.unmodifiableList(collectedIssues);
    }

    public static class ComponentIssue {

        private final Component component;
        private final RuleType type;
        private final String status;
        private final String severity;
        private final String key;
        private final Integer line;
        private final String message;
        private final String resolution;
        private final Long effortInMinutes;

        ComponentIssue(Component component, DefaultIssue issue) {
            this.component = component;
            if (issue != null) { // that's really to make PostAnalysisIssueVisitorTest happy
                this.status = issue.status();
                this.severity = issue.severity();
                this.type = issue.type();
                this.key = issue.key();
                this.line = issue.getLine();
                this.message = issue.getMessage();
                this.resolution = issue.resolution();
                this.effortInMinutes = issue.effortInMinutes();
            } else {
                this.status = null;
                this.severity = null;
                this.type = null;
                this.key = null;
                this.line = null;
                this.message = null;
                this.resolution = null;
                this.effortInMinutes = null;
            }
        }

        public Component getComponent() {
            return component;
        }

        public RuleType getType() {
            return type;
        }

        public String getStatus() {
            return status;
        }

        public String getSeverity() {
            return severity;
        }

        public String getKey() {
            return key;
        }

        @CheckForNull
        public Integer getLine() {
            return line;
        }

        @CheckForNull
        public String getMessage() {
            return message;
        }

        @CheckForNull
        public String getResolution() {
            return resolution;
        }

        @CheckForNull
        public Long getEffortInMinutes() {
            return effortInMinutes;
        }

        @Override
        public int hashCode() {
            return Objects.hash(component, effortInMinutes, key, line, message, resolution, severity, status, type);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            ComponentIssue other = (ComponentIssue) obj;
            return Objects.equals(component, other.component)
                    && Objects.equals(effortInMinutes, other.effortInMinutes)
                    && Objects.equals(key, other.key)
                    && Objects.equals(line, other.line)
                    && Objects.equals(message, other.message)
                    && Objects.equals(resolution, other.resolution)
                    && Objects.equals(severity, other.severity)
                    && Objects.equals(status, other.status)
                    && type == other.type;
        }

    }

}
