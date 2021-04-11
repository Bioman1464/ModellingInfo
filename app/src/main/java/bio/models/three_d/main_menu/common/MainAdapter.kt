package bio.models.three_d.main_menu.common

import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.BaseListAdapter
import bio.models.three_d.main_menu.home.theme.ThemeCell

class MainAdapter(listener: AdapterListener) : BaseListAdapter(
    ThemeCell,
    listener = listener
)