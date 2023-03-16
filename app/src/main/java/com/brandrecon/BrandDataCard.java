package com.brandrecon;

public class BrandDataCard {

    String ceo, field, logoAnimationUrl, logoUrl, name, netWorth, parentCompany, revenue, visitAt;
    public BrandDataCard() {

    }

    public BrandDataCard(String ceo, String field, String logoAnimationUrl, String logoUrl, String name,
                         String netWorth, String parentCompany, String revenue, String visitAt) {

        this.ceo = ceo;
        this.field = field;
        this.logoAnimationUrl = logoAnimationUrl;
        this.logoUrl = logoUrl;
        this.name = name;
        this.netWorth = netWorth;
        this.parentCompany = parentCompany;
        this.revenue = revenue;
        this.visitAt = visitAt;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getLogoAnimationUrl() {
        return logoAnimationUrl;
    }

    public void setLogoAnimationUrl(String logoAnimationUrl) {
        this.logoAnimationUrl = logoAnimationUrl;
    }

    public String getCeo() {
        return ceo;
    }

    public void setCeo(String ceo) {
        this.ceo = ceo;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetWorth() {
        return netWorth;
    }

    public void setNetWorth(String netWorth) {
        this.netWorth = netWorth;
    }

    public String getParentCompany() {
        return parentCompany;
    }

    public void setParentCompany(String parentCompany) {
        this.parentCompany = parentCompany;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getVisitAt() {
        return visitAt;
    }

    public void setVisitAt(String visitAt) {
        this.visitAt = visitAt;
    }

}
