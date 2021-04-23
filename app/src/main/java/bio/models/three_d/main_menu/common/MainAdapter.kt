package bio.models.three_d.main_menu.common

import bio.models.three_d.common.AdapterListener
import bio.models.three_d.common.BaseListAdapter
import bio.models.three_d.main_menu.common.article.ArticleCell
import bio.models.three_d.main_menu.home.theme.ThemeCell
import bio.models.three_d.main_menu.search.search_recycler.SearchArticleCell

class MainAdapter(listener: AdapterListener) : BaseListAdapter(
    ThemeCell, ArticleCell, SearchArticleCell,
    listener = listener
)