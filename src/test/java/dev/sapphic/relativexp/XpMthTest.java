package dev.sapphic.relativexp;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public final class XpMthTest {
  @Test
  public void pointsForLevel3() {
    assertThat(XpMth.pointsForLevel(3)).isEqualTo(27);
  }

  @Test
  public void pointsForLevel14() {
    assertThat(XpMth.pointsForLevel(14)).isEqualTo(280);
  }

  @Test
  public void pointsForLevel15() {
    assertThat(XpMth.pointsForLevel(15)).isEqualTo(315);
  }

  @Test
  public void pointsForLevel29() {
    assertThat(XpMth.pointsForLevel(29)).isEqualTo(1288);
  }

  @Test
  public void pointsForLevel30() {
    assertThat(XpMth.pointsForLevel(30)).isEqualTo(1395);
  }

  @Test
  public void pointsBetweenLevels7And15() {
    assertThat(XpMth.pointsBetweenLevels(7, 15)).isEqualTo(224);
  }

  @Test
  public void pointsBetweenLevels27And30() {
    assertThat(XpMth.pointsBetweenLevels(27, 30)).isEqualTo(306);
  }

  @Test
  public void pointsBetweenLevels30And50() {
    assertThat(XpMth.pointsBetweenLevels(30, 50)).isEqualTo(3950);
  }
}
